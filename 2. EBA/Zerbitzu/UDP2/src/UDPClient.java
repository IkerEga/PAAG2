import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {

        String serverAddress = "127.0.0.1";
        int port = 12345;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress address = InetAddress.getByName(serverAddress);

            System.out.print("Introduce un número: ");
            int numero = scanner.nextInt();

            String numeroTexto = String.valueOf(numero);
            byte[] buffer = numeroTexto.getBytes();

            DatagramPacket packet = new DatagramPacket(
                    buffer,
                    buffer.length,
                    address,
                    port
            );
            socket.send(packet);

            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(
                    bufferRespuesta,
                    bufferRespuesta.length
            );

            socket.receive(respuesta);

            String resultadoTexto = new String(
                    respuesta.getData(), 0, respuesta.getLength());

            System.out.println("Resultado recibido del servidor: " + resultadoTexto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
