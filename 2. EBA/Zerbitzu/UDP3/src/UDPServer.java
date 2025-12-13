import java.net.*;

public class UDPServer {
    public static void main(String[] args) {

        int port = 12345;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Servidor UDP iniciado en el puerto " + port);

            byte[] buffer = new byte[1024];

            DatagramPacket packetNombre = new DatagramPacket(buffer, buffer.length);
            socket.receive(packetNombre);

            String nombre = new String(
                    packetNombre.getData(), 0, packetNombre.getLength());

            InetAddress clientAddress = packetNombre.getAddress();
            int clientPort = packetNombre.getPort();

            DatagramPacket packetEdad = new DatagramPacket(buffer, buffer.length);
            socket.receive(packetEdad);

            int edad = Integer.parseInt(
                    new String(packetEdad.getData(), 0, packetEdad.getLength()));

            System.out.println("Datos recibidos: " + nombre + ", " + edad + " años");

            String respuesta;
            if (edad >= 18) {
                respuesta = nombre + " that is " + edad +
                        " years old, you are of legal age.";
            } else {
                respuesta = nombre + " that is " + edad +
                        " years old, you are not of legal age.";
            }

            byte[] respuestaBytes = respuesta.getBytes();

            DatagramPacket respuestaPacket = new DatagramPacket(
                    respuestaBytes,
                    respuestaBytes.length,
                    clientAddress,
                    clientPort
            );

            socket.send(respuestaPacket);
            System.out.println("Respuesta enviada al cliente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
