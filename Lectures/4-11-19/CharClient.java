import java.net.*;
import java.io.*;
public class CharClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 2019)) {
            OutputStream outputStream = socket.getOutputStream();
            String data = "log_sid";
            byte[] bytes = data.getBytes();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.write(CharServer.END);
            outputStream.flush();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}