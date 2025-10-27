package act4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = scanner.nextLine();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 1234;

            DatagramSocket socket = new DatagramSocket();

            System.out.println("Client connecté. Tapez vos messages ('exit' pour quitter)");

            while (true) {
                System.out.print("Vous : ");
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;

                String fullMsg = "[" + username + "] : " + msg;
                byte[] buffer = fullMsg.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                socket.send(packet);
            }

            socket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

