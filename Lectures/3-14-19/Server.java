import java.io.*;
import java.net.*;
import java.util.*;
public class Server {
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
        return Server.userName;
    }
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1025)) { //port numbers 0-1024 are already used
            // ExecutorService pool = Executors.newFixedThreadPool(100);
            ArrayList<CommHandler> threads = new ArrayList<>();
            while(true) {
                // pool.execute(new CommHandler(serverSocket.accept()));
                CommHandler temp = new CommHandler(serverSocket.accept());
                temp.start();
                threads.add(temp);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}
class ScreenHandler extends Thread {
    private Socket socket;
    ScreenHandler(Socket socket) {
        this.socket = socket;
    }

    public final void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientUsername = reader.readLine();
            while(!socket.isClosed()) {
                System.out.println(clientUsername + ": " + reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}

class CommHandler extends Thread {
    private Socket socket;
    CommHandler(Socket socket) {
        this.socket = socket;
    }
    public final void run() {
        ScreenHandler screenHandler = new ScreenHandler(this.socket);
        screenHandler.start();
        try {
            BufferedReader sokIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sokOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(!socket.isInputShutdown() && !socket.isClosed()) {
                try {
                    String data = sokIn.readLine();
                    if(data == null) {
                        break;
                    }
                    System.out.println(data);
                    System.out.println("Enter Reply: ");
                    sokOut.println(Server.keyIn.readLine());
                    sokOut.flush();
                } catch (IOException ioe) {}
            }
            System.out.println("Connection closed.");
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}