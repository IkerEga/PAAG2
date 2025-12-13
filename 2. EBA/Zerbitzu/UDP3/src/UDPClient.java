import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {

        String serverAddress = "127.0.0.1";
        int port = 12345;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress address = InetAddress.getByName(serverAddress);

            System.out.print("Introduce tu nombre: ");
            String nombre = scanner.nextLine();

            byte[] nombreBytes = nombre.getBytes();
            DatagramPacket packetNombre = new DatagramPacket(
                    nombreBytes,
                    nombreBytes.length,
                    address,
                    port
            );
            socket.send(packetNombre);

            System.out.print("Introduce tu edad: ");
            int edad = scanner.nextInt();

            byte[] edadBytes = String.valueOf(edad).getBytes();
            DatagramPacket packetEdad = new DatagramPacket(
                    edadBytes,
                    edadBytes.length,
                    address,
                    port
            );
            socket.send(packetEdad);

            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(
                    bufferRespuesta,
                    bufferRespuesta.length
            );

            socket.receive(respuesta);

            String mensajeFinal = new String(
                    respuesta.getData(), 0, respuesta.getLength());

            System.out.println("Servidor responde:");
            System.out.println(mensajeFinal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
