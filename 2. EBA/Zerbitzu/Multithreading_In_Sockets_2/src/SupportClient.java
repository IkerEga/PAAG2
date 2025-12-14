import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SupportClient {

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // cambia por la IP del servidor si está en otro PC
        int port = 12345;

        try (
            Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            // Lee el primer mensaje de bienvenida
            String welcome = in.readLine();
            System.out.println(welcome);

            while (true) {
                // 1) Mostrar menú recibido del servidor (son varias líneas)
                //    Sabemos que el menú termina en la línea "Type a number..."
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    if (line.startsWith("Type a number")) break;    //Para que el cliente sepa que ya terminó el menú
                }
                if (line == null) break; // servidor cerró

                // 2) Usuario elige
                String choice = scanner.nextLine();
                out.println(choice);

                // 3) Leer la respuesta del servidor
                //    Si eliges 1-5: el servidor manda "✅ Answer:" + respuesta + línea en blanco
                //    Si eliges 6: manda despedida y cierra
                String response = in.readLine();
                if (response == null) break;

                System.out.println(response);

                // Si es despedida, terminamos
                if (response.contains("Goodbye")) {
                    break;
                }

                // Para respuestas 1-5, normalmente llega otra línea con el texto
                // (y puede haber una línea en blanco después)
                String maybeMore = in.readLine();
                if (maybeMore == null) break;
                System.out.println(maybeMore);

            }

        } catch (IOException e) {
            System.out.println("Client error:");
            e.printStackTrace();
        }
    }
}
