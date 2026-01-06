package library_seats_multithreading;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LibraryClient {

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; 
        int port = 12345;

        try (
            Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            String line;

            while ((line = in.readLine()) != null) {
                System.out.println(line);

                // Si el servidor está pidiendo input, respondemos
                if (line.startsWith("Choose option") || line.startsWith("Enter seat number")) {
                    String userInput = scanner.nextLine();
                    out.println(userInput);
                }
            }

            System.out.println("Connection closed by server.");

        } catch (IOException e) {
            System.out.println("Client error:");
            e.printStackTrace();
        }
    }
}
