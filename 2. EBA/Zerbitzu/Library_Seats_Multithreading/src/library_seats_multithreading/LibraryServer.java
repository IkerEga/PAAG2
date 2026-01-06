package library_seats_multithreading;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryServer {

    private static final int PORT = 12345;
    private static final int TOTAL_SEATS = 10;

    // Recurso compartido: true = reservado, false = libre
    private static final boolean[] seats = new boolean[TOTAL_SEATS];

    public static void main(String[] args) {
        // Pool fijo: limita cuántos clientes se atienden "a la vez"
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Library Server running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            System.out.println("Server error:");
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    
    private static synchronized String viewSeats() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Seats status ===\n");
        for (int i = 0; i < TOTAL_SEATS; i++) {
            sb.append("Seat ").append(i + 1).append(": ")
              .append(seats[i] ? "RESERVED" : "AVAILABLE")
              .append("\n");
        }
        
        /*  LO MISMO QUE ESTO ES EL OPERADOR TERNARIO
        
            if (seats[i]) {
                "RESERVED"
            } else {
                "AVAILABLE"
            }
        */
        return sb.toString();
    }

    private static synchronized String reserveSeat(int seatNumber) {
        int idx = seatNumber - 1;
        if (idx < 0 || idx >= TOTAL_SEATS) {
            return "Invalid seat number. Choose 1 to " + TOTAL_SEATS + ".";
        }
        if (seats[idx]) {
            return "Seat " + seatNumber + " is already RESERVED.";
        }
        seats[idx] = true;
        return "Seat " + seatNumber + " RESERVED successfully.";
    }

    private static synchronized String cancelSeat(int seatNumber) {
        int idx = seatNumber - 1;
        if (idx < 0 || idx >= TOTAL_SEATS) {
            return "Invalid seat number. Choose 1 to " + TOTAL_SEATS + ".";
        }
        if (!seats[idx]) {
            return "Seat " + seatNumber + " is already AVAILABLE (nothing to cancel).";
        }
        seats[idx] = false;
        return "Reservation for seat " + seatNumber + " CANCELLED successfully.";
    }

    // --- Hilo por cliente ---
    private static class ClientHandler implements Runnable {
        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                Socket s = socket;
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)
            ) {
                out.println("Welcome to the Library Seat Reservation System!");
                boolean running = true;

                while (running) {
                    // Menú principal
                    out.println("=== MENU ===");
                    out.println("1) View available seats");
                    out.println("2) Reserve a seat");
                    out.println("3) Cancel a reservation");
                    out.println("4) Exit");
                    out.println("Choose option (1-4):");

                    String line = in.readLine();
                    if (line == null) {
                        break; // cliente desconectado
                    }

                    int option;
                    try {
                        option = Integer.parseInt(line.trim());
                    } catch (NumberFormatException e) {
                        out.println("Please enter a valid number (1-4).");
                        continue;
                    }

                    switch (option) {
                        case 1 -> {
                            out.println(viewSeats());
                        }
                        case 2 -> {
                            out.println("Enter seat number to RESERVE (1 - " + TOTAL_SEATS + "):");
                            String seatLine = in.readLine();
                            if (seatLine == null) break;

                            int seatNum;
                            try {
                                seatNum = Integer.parseInt(seatLine.trim());
                            } catch (NumberFormatException e) {
                                out.println("Invalid seat number. Must be an integer.");
                                break;
                            }

                            out.println(reserveSeat(seatNum));
                        }
                        case 3 -> {
                            out.println("Enter seat number to CANCEL (1 - " + TOTAL_SEATS + "):");
                            String seatLine = in.readLine();
                            if (seatLine == null) break;

                            int seatNum;
                            try {
                                seatNum = Integer.parseInt(seatLine.trim());
                            } catch (NumberFormatException e) {
                                out.println("Invalid seat number. Must be an integer.");
                                break;
                            }

                            out.println(cancelSeat(seatNum));
                        }
                        case 4 -> {
                            out.println("Goodbye! Thanks for using the system.");
                            running = false;
                        }
                        default -> out.println("Option out of range. Choose 1-4.");
                    }
                }

            } catch (IOException e) {
                System.out.println("Client handler error: " + e.getMessage());
            } finally {
                System.out.println("Client session closed: " + socket.getInetAddress());
            }
        }
    }
}
