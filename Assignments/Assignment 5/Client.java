import java.io.*;
import java.net.*;
public class Client {
    private static String userName;
    static {
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter your username: ");
            userName = keyIn.readLine();
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
    public static void main(String[] args) {
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
        try (Socket socket = new Socket("LocalHost", 4096)) {
            PrintWriter sokOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            GetMessage getMessage = new GetMessage(socket);
            getMessage.start();
            sokOut.println(userName);
            sokOut.flush();
            while(true) {
                System.out.println("Enter destination username: ");
                sokOut.println(keyIn.readLine());
                System.out.println("Enter a message: ");
                sokOut.println(keyIn.readLine());
                sokOut.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}
class GetMessage extends Thread {
    Socket socket = new Socket();
    GetMessage(Socket socket) {
        this.socket = socket;
        setDaemon(true);
    }
    public final void run() {
        try {
            BufferedReader sokIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                String sender = sokIn.readLine();
                String message = sokIn.readLine();
                System.out.println(sender + ": " + message);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}