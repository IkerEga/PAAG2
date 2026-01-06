import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExamServer {
    private static final int PORT = 12345;

    // Preguntas (enviamos texto ya “formateado”)
    private static final String[] QUESTIONS = {
            "1) ¿Cuál de los siguientes es un protocolo de transporte orientado a la conexión?\n" +
                    "a) TCP\nb) UDP\nc) IP",

            "2) ¿A qué tipo de aplicación beneficia más el protocolo UDP?\n" +
                    "a) Transferencia de archivos\nb) Videoconferencia y transmisión de voz\nc) Navegación web",

            "3) ¿Cuál es la función del campo \"Reconocimiento\" (ACK) en la cabecera TCP?\n" +
                    "a) Confirmación de entrega de datos\nb) Establecimiento de la conexión inicial\nc) Gestión de la derivación",

            "4) ¿Cuál de los siguientes es un protocolo de transporte sin conexión?\n" +
                    "a) TCP\nb) UDP\nc) FTP",

            "5) ¿Qué tipo de servicio ofrece TCP con respecto a UDP?\n" +
                    "a) Servicio no fiable\nb) Servicio fiable y orientado a la conexión\nc) Dirigido a la televisión"
    };

    // Respuestas correctas correspondientes (a/b/c)
    private static final String[] CORRECT = { "a", "b", "a", "b", "b" };

    // Nota de corte (puedes cambiarla). Ej: 3/5 para aprobar.
    private static final int PASS_SCORE = 3;

    public static void main(String[] args) {
        // 1) Preguntar cuántos alumnos podrán hacer el examen A LA VEZ
        Scanner console = new Scanner(System.in);
        System.out.print("¿Cuántos alumnos podrán hacer el examen simultáneamente? ");
        int maxStudents;
        while (true) {
            String line = console.nextLine().trim();
            try {
                maxStudents = Integer.parseInt(line);
                if (maxStudents <= 0) {
                    System.out.print("Debe ser > 0. Inténtalo otra vez: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Escribe un número válido: ");
            }
        }

        // 2) Pool de hilos: limita conexiones simultáneas
        ExecutorService pool = Executors.newFixedThreadPool(maxStudents);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor de examen iniciado en puerto " + PORT);
            System.out.println("Máximo simultáneo: " + maxStudents);

            // 3) Aceptar clientes en bucle y delegar a hilos
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            System.out.println("Error del servidor:");
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    // Hilo por cliente (aquí ocurre el examen completo)
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
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                out.println("Bienvenido/a al examen tipo test.");
                out.println("Responde con: a, b o c.");
                out.println("Cuando termines, recibirás tu nota.\n");

                int score = 0;

                for (int i = 0; i < QUESTIONS.length; i++) {
                    // 1) Enviar pregunta (marcador para que el cliente sepa que empieza)
                    out.println("QUESTION_START");
                    out.println(QUESTIONS[i]);
                    out.println("Tu respuesta (a/b/c): ");

                    // 2) Leer respuesta del cliente
                    String answer = in.readLine();
                    if (answer == null) {
                        // el cliente se fue
                        return;
                    }
                    answer = answer.trim().toLowerCase();

                    // 3) Validación sencilla (si responde mal formato, cuenta como mal)
                    if (answer.equals(CORRECT[i])) {
                        score++;
                    }
                }
                //                          CON WHILE
                // int i = 0;

                // while (i < QUESTIONS.length) {
                //     out.println(QUESTIONS[i]);
                //     out.println("Tu respuesta (a/b/c):");

                //     String answer = in.readLine();
                //     if (answer == null) {
                //         return;
                //     }

                //     if (answer.equals(CORRECT[i])) {
                //         score++;
                //     }

                //     i++;
                // }

                // 4) Resultado final
                out.println("RESULT_START");
                out.println("Examen finalizado.");
                out.println("Tu puntuación: " + score + " / " + QUESTIONS.length);

                if (score >= PASS_SCORE) {
                    out.println("HAS APROBADO (>= " + PASS_SCORE + ").");
                } else {
                    out.println("NO HAS APROBADO (necesitas >= " + PASS_SCORE + ").");
                }
                out.println("Gracias por realizar el examen. ¡Hasta luego!");
                out.println("RESULT_END");

            } catch (IOException e) {
                System.out.println("Error con un cliente: " + e.getMessage());
            } finally {
                System.out.println("Sesión cerrada: " + socket.getInetAddress());
            }
        }
    }
}
