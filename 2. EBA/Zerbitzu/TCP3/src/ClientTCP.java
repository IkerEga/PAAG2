import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 12345;

        try (
                Socket socket = new Socket(serverAddress, port);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);
            ) {
                System.out.println("Conectado al servidor en " + serverAddress + ":" + port);

                System.out.print("Ingrese su nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese su edad: ");
                int edad = scanner.nextInt();

                out.writeUTF(nombre);
                out.writeInt(edad);

                String respuesta = in.readUTF();
                System.out.println("Respuesta del servidor: " + respuesta);

        } catch (IOException e) {
            System.err.println("Error de conexion al servidor: " + e.getMessage());
        }
    }
}
