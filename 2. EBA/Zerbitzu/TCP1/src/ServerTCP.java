import java.io.*;
import java.net.*;

public class ServerTCP {
    public static void main(String[] args) {

        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);
            System.out.println("Esperando a un cliente...");

            // Espera a que un cliente se conecte
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " 
                    + clientSocket.getInetAddress());

            // Streams
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));

            PrintWriter writer = new PrintWriter(outputStream, true);

            // 🔹 1. El servidor envía un saludo al cliente
            writer.println("Hola cliente, bienvenido al servidor");

            // 🔹 2. El servidor recibe la respuesta del cliente
            String respuestaCliente = reader.readLine();
            System.out.println("Respuesta del cliente: " + respuestaCliente);

            // Cerrar todo
            clientSocket.close();
            serverSocket.close();
            System.out.println("Conexión cerrada");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
