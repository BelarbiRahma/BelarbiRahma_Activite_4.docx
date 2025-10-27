package act4;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress(1234);
            socket.bind(address);
            System.out.println("Serveur UDP démarré ");

            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String message = new String(packet.getData(), 0, packet.getLength());
                    String clientIP = packet.getAddress().getHostAddress();
                    int clientPort = packet.getPort();

                    System.out.println("[" + clientIP + ":" + clientPort + "] → " + message);

                } catch (SocketException e) {
                    System.out.println("Serveur arrêté proprement.");
                    break; 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
