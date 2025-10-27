package act4;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = scanner.nextLine();

            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 1234;

            System.out.println("\n Client connecté. Vous pouvez envoyer des messages ('exit' pour quitter)\n");

            // Thread de réception
            Thread receiveThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String message = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("\n" + message);
                        System.out.print("Vous : ");
                    }
                } catch (Exception e) {
                    System.out.println(" Réception arrêtée.");
                }
            });

            receiveThread.start();

            // Thread principal → envoi des messages
            while (true) {
                System.out.print("Vous : ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println(" Déconnexion...");
                    socket.close();
                    break;
                }

                String fullMessage = "[" + username + "] : " + message;
                byte[] data = fullMessage.getBytes();

                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
                socket.send(packet);
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

