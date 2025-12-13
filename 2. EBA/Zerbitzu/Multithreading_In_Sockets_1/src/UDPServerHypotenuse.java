import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPServerHypotenuse {

    public static void main(String[] args) {
        final int port = 12345;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Servidor UDP listo en puerto " + port);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String data = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8).trim();
                
                String[] parts = data.split("\\s+");    // Esperamos algo como: "3.0 4.0"

                String responseText;

                if (parts.length != 2) {
                    responseText = "ERROR: send two numbers like: '3 or 4'";
                } else {
                    try {
                        double a = Double.parseDouble(parts[0]);
                        double b = Double.parseDouble(parts[1]);

                        double h = Math.hypot(a, b); // calcula sqrt(a*a + b*b) de forma segura
                        responseText = String.valueOf(h);

                        System.out.println("Recibido: a = " + a + ", b = " + b + " ---> h = " + h);
                    } catch (NumberFormatException e) {
                        responseText = "ERROR: invalid number format";
                    }
                }

                byte[] responseBytes = responseText.getBytes(StandardCharsets.UTF_8);
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();

                DatagramPacket response = new DatagramPacket(
                        responseBytes, responseBytes.length, clientAddress, clientPort
                );
                socket.send(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
