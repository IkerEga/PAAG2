package quiz_with_rankig;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author egana.iker
 */
public class QuizClient {

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 12345;

        try (
                Socket socket = new Socket(serverAddress, port); 
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); 
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true); 
                Scanner scanner = new Scanner(System.in);) {
            System.out.println("Conectado con el servidor");

            while (true) {
                String line = in.readLine();

                if (line == null) {
                    break;
                }

                if (line.equals("QUESTION_START")) {
                    String qLine = in.readLine();
                    if (qLine == null) return;
                    
                    System.out.println(qLine);
                    
                    if (qLine.equals("Tu respuesta (a/b/c): ")) {
                        break;
                    }
                    
                    String answer;
                    while (true) {
                        answer = scanner.nextLine().trim().toLowerCase();
                        if (answer.equals("a") || answer.equals("b") || answer.equals("c")) break;
                        System.out.print("Escribe solo a, b o c: ");
                    }
                    out.println(answer);
                    
                }else if (line.equals("RESULT_START")) {
                    while (true) {
                        String rLine = in.readLine();
                        if (rLine == null) return;
                        if (rLine.equals("RESULT_END")) break;
                        
                        System.out.println(rLine);
                    }
                    break;
                } else {
                    System.out.println(line);
                }

            }
            System.out.println("Cliente cerrado");
        } catch (IOException e) {
            System.out.println("Error en el cliente: ");
            e.printStackTrace();
        }
    }
}
