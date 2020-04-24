import java.net.*;
import java.io.*;
import java.util.*;
public class TCPConnect {
    public static void main(String[] args) {
        try (Socket socket = new Socket("10.40.95.44", 8191)) {
            PrintWriter sokOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(!socket.isClosed() && !socket.isOutputShutdown()) {
                sokOut.println("I Love BC!");
                sokOut.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
    }
}