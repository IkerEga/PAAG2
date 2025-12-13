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
                
            System.out.println("Conectado al servidor");
            
            System.out.println("Introduce un numero: ");
            int numero = scanner.nextInt();

            out.writeInt(numero);

            int resultado = in.readInt();
            System.out.println("El servidor ha respondido: " + resultado);

            System.out.println("Conexión cerrada");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
