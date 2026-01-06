package quiz_with_rankig;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizServer {

    private static final int PORT = 12345;

    private static List<String> ranking = new ArrayList<>();

    private static final String[] QUESTIONS = {
        "1) ¿Cuál de los siguientes es un protocolo de transporte orientado a la conexión?\n"
        + "a) TCP\nb) UDP\nc) IP",
        "2) ¿A qué tipo de aplicación beneficia más el protocolo UDP?\n"
        + "a) Transferencia de archivos\nb) Videoconferencia y transmisión de voz\nc) Navegación web",
        "3) ¿Cuál es la función del campo \"Reconocimiento\" (ACK) en la cabecera TCP?\n"
        + "a) Confirmación de entrega de datos\nb) Establecimiento de la conexión inicial\nc) Gestión de la derivación",
        "4) ¿Cuál de los siguientes es un protocolo de transporte sin conexión?\n"
        + "a) TCP\nb) UDP\nc) FTP",
        "5) ¿Qué tipo de servicio ofrece TCP con respecto a UDP?\n"
        + "a) Servicio no fiable\nb) Servicio fiable y orientado a la conexión\nc) Dirigido a la televisión"
    };

    private static final String[] CORRECT = {"a", "b", "a", "b", "b"};

    private static synchronized String añadirClasificacion() {

        return null;
    }

    private static synchronized String calcularClasificacion() {

        return null;
    }

    public static void main(String[] args) throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(20);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor corriendo en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Error en servidor");
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
                    Socket s = socket; 
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); 
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) 
            {
                out.println("Bienvenido al QUIZ !!");
                boolean running = true;
                
                while(running) {
                    
                    int score = 0;
                    
                    out.println("=== MENU ===");
                    out.println("1) Start Quiz");
                    out.println("2) View Top 5 ranking");
                    out.println("3) View my last score");
                    out.println("4) Exit");
                    out.println("Choose option (1-4):");
                    
                    String line = in.readLine();
                    
                    if (line == null) break;
                    
                    int option;
                    try {
                        option = Integer.parseInt(line.trim());
                    } catch (NumberFormatException e) {
                        out.println("Porfavor, solo introduce un numero del 1-4");
                        continue;
                    }
                    
                    switch (option) {
                        case 1 -> {
                            for (int i = 0; i < QUESTIONS.length; i++) {
                                out.println("QUESTION_START");
                                out.println(QUESTIONS[i]);
                                out.println("Tu respuesta (a/b/c): ");

                                String answer = in.readLine();
                                if (answer == null) {
                                    return;
                                }
                                answer = answer.trim().toLowerCase();

                                if (answer.equals(CORRECT[i])) {
                                    score++;
                                }
                            }
                        }
                        case 2 -> {}
                        case 3-> {}
                        case 4 -> {
                            System.out.println("Muchas gracias!! Adios!!");
                            running = false;
                        }
                        
                        default -> out.println("Solo opciones de 1-4");
                    }
                }
                
            } catch (IOException e) {
                System.out.println("Client handler error: " + e.getMessage());
            } finally {
                System.out.println("Sesion del cliente cerrada: " + socket.getInetAddress());
            }
        }
    }

}
