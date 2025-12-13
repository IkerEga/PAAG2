import java.io.*;
import java.net.*;

public class ClientTCP {
    public static void main(String[] args) {

        String serverAddress = "127.0.0.1"; // mismo ordenador
        int port = 12345;

        try {
            // Conectar al servidor
            Socket socket = new Socket(serverAddress, port);
            System.out.println("Conectado al servidor");

            // Streams
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));

            PrintWriter writer = new PrintWriter(outputStream, true);

            // 🔹 1. El cliente recibe el saludo del servidor
            String saludoServidor = reader.readLine();
            System.out.println("Servidor dice: " + saludoServidor);

            // 🔹 2. El cliente responde con su nombre
            String nombre = "Alex";  // puedes cambiarlo por el tuyo
            writer.println("Hola servidor, soy " + nombre);

            // Cerrar conexión
            socket.close();
            System.out.println("Conexión cerrada");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
