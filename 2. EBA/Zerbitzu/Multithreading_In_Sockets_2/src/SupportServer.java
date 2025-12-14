import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SupportServer {

    private static final int PORT = 12345;

    // Respuestas del "soporte técnico"
    private static final String[] ANSWERS = {
            "You can reset your password by following the recovery steps on the login page.",

            "Go to the Wi-Fi settings on your device, choose the available network, and enter the password if necessary.",

            "Check the printer connection, ink/paper levels, and restart the printer and computer if necessary.",

            "Visit the operating system settings, search for 'Updates,' and follow the instructions to check and apply available updates.",

            "You can back up your important files using cloud storage services or external storage devices like USB."
    };

    private static final String MENU = String.join("\n",
            "\n=== Technical Support Menu ===",
            "1) How to reset my account password?",
            "2) How can I connect my device to a Wi-Fi network?",
            "3) How to troubleshoot printing issues on my printer?",
            "4) What steps should I follow to update my operating system software?",
            "5) How can I back up my important files?",
            "6) Exit",
            "Type a number (1-6) and press Enter:");

    public static void main(String[] args) {
        // Pool de hilos: reutiliza hilos y evita crear infinitos
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Support server running on port " + PORT);

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

    private static class ClientHandler implements Runnable {
        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    Socket s = socket; // así se cierra al salir del try
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)
                ) {

                out.println("Welcome to Technical Support!");
                boolean running = true;

                while (running) {
                    // 1) Enviar menú
                    out.println(MENU);

                    // 2) Leer elección del cliente
                    String line = in.readLine();
                    if (line == null) {
                        // Cliente cerró la conexión sin decir "Exit"
                        break;
                    }

                    int option;
                    try {
                        option = Integer.parseInt(line.trim());
                    } catch (NumberFormatException nfe) {
                        out.println("Invalid input. Please type a number from 1 to 6.");
                        continue;
                    }

                    // 3) Responder según opción
                    if (option >= 1 && option <= 5) {
                        out.print("Answer: ");
                        out.println(ANSWERS[option - 1]);
                    } else if (option == 6) {
                        out.println("Thanks for using Technical Support. Goodbye!");
                        running = false;
                    } else {
                        out.println("Option out of range. Please choose 1 to 6.");
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
