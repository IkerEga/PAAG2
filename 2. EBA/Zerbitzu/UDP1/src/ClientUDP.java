import java.net.*;

public class ClientUDP {
    public static void main(String[] args) {

        String serverAddress = "127.0.0.1";
        int port = 12345;

        try (DatagramSocket socket = new DatagramSocket()) {

            InetAddress address = InetAddress.getByName(serverAddress);

            String mensajeInicial = "Hola servidor";
            byte[] buffer = mensajeInicial.getBytes();

            DatagramPacket packet = new DatagramPacket(
                    buffer,
                    buffer.length,
                    address,
                    port
            );
            socket.send(packet);

            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket respuestaServidor = new DatagramPacket(
                    bufferRespuesta, bufferRespuesta.length);

            socket.receive(respuestaServidor);

            String saludoServidor = new String(
                    respuestaServidor.getData(), 0, respuestaServidor.getLength());

            System.out.println("Servidor: " + saludoServidor);

            String saludoPersonalizado = "Hola servidor, soy Alex";
            byte[] saludoBytes = saludoPersonalizado.getBytes();

            DatagramPacket saludoPacket = new DatagramPacket(
                    saludoBytes,
                    saludoBytes.length,
                    address,
                    port
            );
            socket.send(saludoPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
