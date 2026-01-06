import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ExamClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // pon la IP del servidor si es otro PC
        int port = 12345;

        try (
            Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectado al servidor de examen.");

            while (true) {
                String line = in.readLine();
                if (line == null) {
                    // el servidor cerró
                    break;
                }

                // El servidor puede mandarte mensajes normales o preguntas
                if (line.equals("QUESTION_START")) {
                    // 1) Imprimir la pregunta completa hasta el marcador de respuesta
                    while (true) {
                        String qLine = in.readLine();
                        if (qLine == null) return;

                        System.out.println(qLine);

                        if (qLine.equals("Tu Respuesta (a/b/c):" )) {
                            break;
                        }
                    }

                    // 2) Leer respuesta del usuario y enviarla
                    String answer;
                    while (true) {
                        answer = scanner.nextLine().trim().toLowerCase();
                        if (answer.equals("a") || answer.equals("b") || answer.equals("c")) break;
                        System.out.print("Escribe solo a, b o c: ");
                    }
                    out.println(answer);

                } else if (line.equals("RESULT_START")) {
                    // Imprimir el resultado final hasta RESULT_END
                    while (true) {
                        String rLine = in.readLine();
                        if (rLine == null) return;
                        if (rLine.equals("RESULT_END")) break;
                        System.out.println(rLine);
                    }
                    break; // fin del examen

                } else {
                    // Mensaje informativo
                    System.out.println(line);
                }
            }

            System.out.println("Cliente cerrado.\n");
        } catch (IOException e) {
            System.out.println("Error en el cliente:");
            e.printStackTrace();
        }
    }
}
