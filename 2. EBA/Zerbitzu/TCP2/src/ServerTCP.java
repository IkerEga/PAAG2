import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

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
                    System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

                    int numeroRecibido = in.readInt();
                    System.out.println("Numero recibido del cliente.: " + numeroRecibido);

                    int sumarNumero = new Random().nextInt(10) + 1;
                    System.out.println("El servidor suma: " + sumarNumero);

                    int resultado = numeroRecibido + sumarNumero;
                    out.writeInt(resultado);
                    System.out.println("Resultado enviado al cliente: " + resultado);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}