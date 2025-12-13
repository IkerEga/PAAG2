import java.net.*;

public class ServerUDP {
    public static void main(String[] args) {

        int port = 12345;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Servidor UDP iniciado en el puerto " + port);

            byte[] buffer = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String mensajeCliente = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Mensaje inicial del cliente: " + mensajeCliente);

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            String saludoServidor = "Hola cliente, bienvenido al servidor UDP";
            byte[] saludoBytes = saludoServidor.getBytes();

            DatagramPacket saludoPacket = new DatagramPacket(
                    saludoBytes,
                    saludoBytes.length,
                    clientAddress,
                    clientPort
            );
            socket.send(saludoPacket);

            DatagramPacket respuestaPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(respuestaPacket);

            String saludoPersonalizado = new String(
                    respuestaPacket.getData(), 0, respuestaPacket.getLength());

            System.out.println("Saludo personalizado: " + saludoPersonalizado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
