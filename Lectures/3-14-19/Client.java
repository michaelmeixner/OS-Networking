import java.io.*;
import java.net.*;
public class Client {
    static BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
    private static String userName;
    static {
        try {
            System.out.println("Enter your username: ");
            userName = keyIn.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }

    public String getUsername() {
        return Client.userName;
    }
    public static void main(String[] args) {
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
        try (Socket socket = new Socket("LocalHost", 1025)) {
            PrintWriter sokOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader sokIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                System.out.println(sokIn.readLine());
                sokOut.println(keyIn.readLine());
                sokOut.flush();
                System.out.println(sokIn.readLine());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}