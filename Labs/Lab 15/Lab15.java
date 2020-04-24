// Michael Meixner, Lab 15
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
public class Lab15 {
    public static InetAddress address;
    static {
        try {
            address = InetAddress.getByName("10.40.11.41");
        } catch (UnknownHostException e) {
            //TODO: handle exception
        }
    }
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            DatagramSocket listenSocket = new DatagramSocket(3072); 
            // DatagramSocket sendSocket = new DatagramSocket(3072);
            // sendSocket.connect(InetAddress.getByName(address), 3072);
            System.out.println("Enter your name: ");
            String myName = reader.readLine();
            ReceiveData rdData = new ReceiveData(listenSocket);
            rdData.start();
            while (true) {
                System.out.println("Enter name to send message to: ");
                String recipient = reader.readLine();
                System.out.println("Enter message to send: ");
                String message = reader.readLine();
                byte[] packet = new byte[1024];
                DatagramPacket messagePacket = new DatagramPacket(packet, 1024);
                byte[] name = myName.getBytes(StandardCharsets.UTF_8);
                byte[] name1 = recipient.getBytes(StandardCharsets.UTF_8);
                byte[] toSend = message.getBytes(StandardCharsets.UTF_8);
                System.arraycopy(name, 0, packet, 0, name.length);
                System.arraycopy(name1, 0, packet, 40, name1.length);
                System.arraycopy(toSend, 0, packet, 80, toSend.length);
                messagePacket.setAddress(address);
                messagePacket.setPort(3072);
                listenSocket.send(messagePacket);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}
class ReceiveData extends Thread {
    private DatagramSocket socket;
    public ReceiveData(DatagramSocket socket) {
        this.socket = socket;
    }
    public void run() {
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        try {
            while(!socket.isClosed()) {
                socket.receive(packet);
                byte[] data = packet.getData();
                String sender = new String(data, 0, 40).trim();
                String receiver = new String(data, 40, 40).trim();
                String message = new String(data, 80, 944).trim();
                if(receiver.equals("Michael")) {
                    System.out.println("Sender: " + sender + "\nMessage: " + message);
                } else {
                    //change the to address to John
                    //change the to port
                    //send packet
                    packet.setAddress(Lab15.address);
                    packet.setPort(3072);
                    socket.send(packet);
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}