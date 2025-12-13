import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClientHypotenuse {

    public static void main(String[] args) {

        final String serverAddress = "127.0.0.1"; 
        final int port = 12345;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            System.out.print("Cateto a: ");
            double a = sc.nextDouble();

            System.out.print("Cateto b: ");
            double b = sc.nextDouble();

            String payload = a + " " + b;
            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);

            InetAddress address = InetAddress.getByName(serverAddress);
            DatagramPacket request = new DatagramPacket(payloadBytes, payloadBytes.length, address, port);
            socket.send(request);

            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            String resultText = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);

            if (resultText.startsWith("ERROR")) {
                System.out.println("Servidor respondió: " + resultText);
            } else {
                double h = Double.parseDouble(resultText);
                System.out.println("Hipotenusa = " + h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
