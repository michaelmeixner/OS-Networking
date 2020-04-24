import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.io.*;
import java.util.*;
public class UDPQuestion {
    public static InetAddress address;
    static{
        try {
            address = InetAddress.getByName("10.40.95.44");
        } catch (UnknownHostException uhe) {}
    }
    public static void main(String[] args) {
        File file = new File("C:\\Users\\9mich\\Documents\\OS and Networking\\Exams\\Final\\UDPQuestion.java");
        long fileSize = file.length();
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt((int)fileSize);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            DatagramSocket socket = new DatagramSocket();
            System.out.println("Enter your name: ");
            String name = reader.readLine();
            byte[] packet = new byte[4096];
            DatagramPacket messagePacket = new DatagramPacket(packet, 4096);
            byte[] nameToSend = name.getBytes(StandardCharsets.US_ASCII);
            byte[] size = buffer.array();
            byte[] fileName = file.getName().getBytes(StandardCharsets.US_ASCII);
            byte[] fileContents = Files.readAllBytes(file.toPath());
            System.arraycopy(nameToSend, 0, packet, 0, nameToSend.length);
            System.arraycopy(size, 0, packet, 50, size.length);
            System.arraycopy(fileName, 0, packet, 54, fileName.length);
            System.arraycopy(fileContents, 0, packet, 104, fileContents.length);
            messagePacket.setAddress(address);
            messagePacket.setPort(8193);
            socket.send(messagePacket);
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}