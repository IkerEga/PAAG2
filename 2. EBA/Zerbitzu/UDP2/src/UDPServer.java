import java.net.*;
import java.util.Random;

public class UDPServer {
    public static void main(String[] args) {

        int port = 12345;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Servidor UDP iniciado en el puerto " + port);

            byte[] buffer = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String numeroTexto = new String(packet.getData(), 0, packet.getLength());
            int numeroRecibido = Integer.parseInt(numeroTexto);

            System.out.println("Número recibido: " + numeroRecibido);

            int numeroASumar = new Random().nextInt(10) + 1; // 1 a 10
            System.out.println("El servidor suma: " + numeroASumar);

            int resultado = numeroRecibido + numeroASumar;

            String resultadoTexto = String.valueOf(resultado);
            byte[] resultadoBytes = resultadoTexto.getBytes();

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            DatagramPacket respuesta = new DatagramPacket(
                    resultadoBytes,
                    resultadoBytes.length,
                    clientAddress,
                    clientPort
            );

            socket.send(respuesta);

            System.out.println("Resultado enviado al cliente: " + resultado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
