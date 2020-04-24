import java.io.*;
import java.net.*;
import java.util.*;
public class Server {
    static BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4096)) {
            ArrayList<CommHandler> threads = new ArrayList<>();
            while(true) {
                CommHandler temp = new CommHandler(serverSocket.accept());
                temp.start();
                threads.add(temp);
                System.out.println("Connected to Client.");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}

class CommHandler extends Thread {
    private Socket socket;
    private String clientUsername;
    private static Map<String, PrintWriter> socketMap = new HashMap<>();
    CommHandler(Socket socket) {
        this.socket = socket;
    }
    public final void run() {
        try {
            BufferedReader sokIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sokOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            try {
                clientUsername = sokIn.readLine();
                socketMap.put(clientUsername, sokOut);
            } catch (IOException ioe) {}
            while(!socket.isInputShutdown() && !socket.isClosed()) {
                try {
                    String destUsername = sokIn.readLine();
                    String message = sokIn.readLine();
                    PrintWriter output = socketMap.get(destUsername);
                    output.println(clientUsername);
                    output.println(message);
                    output.flush();
                } catch (IOException ioe) {}
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}