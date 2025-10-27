package act4;
import java.net.*;
import java.util.*;

public class UDPServer {
    public static void main(String[] args) {
        try {
            // 1️ Création d’un socket UDP 
            DatagramSocket socket = new DatagramSocket(1234);

            // Liste pour stocker les adresses des clients connectés
            Set<SocketAddress> clients = new HashSet<>();

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                SocketAddress clientAddress = packet.getSocketAddress();

                // Ajouter le client s’il n’est pas encore dans la liste
                clients.add(clientAddress);

                System.out.println("Reçu de " + clientAddress + " → " + message);

                // Diffuser le message à tous les autres clients
                for (SocketAddress addr : clients) {
                    if (!addr.equals(clientAddress)) {
                        byte[] sendData = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addr);
                        socket.send(sendPacket);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
