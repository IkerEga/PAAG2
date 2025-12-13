import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) {
        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {

                System.out.println("Esperando a un cliente...");

                try (
                        Socket clientSocket = serverSocket.accept();
                        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
                    ) {

                    System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                    String nombre = in.readUTF();
                    System.out.println("Nombre recibido: " + nombre);

                    int edad = in.readInt();
                    System.out.println("Edad recibida: " + edad);

                    if (edad >= 18) {
                        out.writeUTF("Hola " + nombre + ", eres mayor de edad.");
                    } else {
                        out.writeUTF("Hola " + nombre + ", eres menor de edad.");
                    }

                } catch (IOException e) {
                    System.err.println("Error al manejar la conexion con el cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
