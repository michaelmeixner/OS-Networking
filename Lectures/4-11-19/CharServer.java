import java.net.*;
import java.io.*;

public class CharServer {
    public static final int END = 1073741824;
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(2019)) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            int data = 0;
            while((data = inputStream.read()) < END) {
                System.out.println(Character.getName(data));
            }

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}