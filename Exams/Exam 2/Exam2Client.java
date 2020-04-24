// Michael Meixner, Exam 2
import java.io.*;
import java.net.Socket;
public class Exam2Client {
    static String serverName, data;
    static int portNum;
    public static void main(String[] args) {
        String[] info = new String[4];
        try (Socket socket = new Socket("127.0.0.1", 4907)) {
            BufferedReader sokIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverName = sokIn.readLine();
            portNum = Integer.parseInt(sokIn.readLine());
            while(!socket.isInputShutdown() && !socket.isClosed() && !(sokIn.readLine() == null)) {
                for(int i = 0; i < info.length; i++) {
                    info[i] = sokIn.readLine();
                }
            }
            socket.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        try (Socket socket2 = new Socket(serverName, portNum)) {
            BufferedReader sokIn2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            PrintWriter sokOut = new PrintWriter(new OutputStreamWriter(socket2.getOutputStream()));
            for(int i = 0; i < info.length; i++) {
                sokOut.print(info[i]);
                sokOut.flush();
            }
            while(!socket2.isInputShutdown() && !socket2.isClosed() && !(sokIn2.readLine() == null)) {
                System.out.println(sokIn2.readLine());
            }
            socket2.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}