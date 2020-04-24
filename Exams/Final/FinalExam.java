// Michael Meixner, OS & Networking Final Exam
import java.net.*;
import java.io.*;
import java.util.*;
public class FinalExam {
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(8192)) {
            ArrayList<GetInput> threads = new ArrayList<>(1000);
            while(true) {
                GetInput getInput = new GetInput(socket.accept());
                getInput.start();
                threads.add(getInput);
                System.out.println("Connected to Client.");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
class GetInput extends Thread {
    private Socket socket;
    GetInput(Socket socket) {
        this.socket = socket;
    }
    public final void run() {
        try {
            BufferedReader sokIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!socket.isInputShutdown() && !socket.isClosed()) {
                String data = sokIn.readLine();
                if(data == null) {
                    break;
                } else {
                    System.out.println(data);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}