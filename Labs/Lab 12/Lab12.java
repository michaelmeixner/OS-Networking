// Michael Meixner, Lab 12
import java.io.*;
import java.net.*;
public class Lab12 {
    public static void main(String[] args) {
        String address = KeyboardReader.readLine("Please enter a URL:");
        StringBuilder builder = new StringBuilder();
        int portNum = 80;
        int i = 0;
        if(address.charAt(0) == 'h') {
            int slashes = 0;
            while(i < address.length() && slashes < 2) {
                builder.append(address.charAt(i));
                if(address.charAt(i) == 's') {
                    portNum = 443;
                }
                if(address.charAt(i++) == '/') {
                    slashes++;
                }
            }
        }
        while(address.charAt(i) != '/') {
            builder.append(address.charAt(i++));
        }
        String domain = builder.toString();
        builder = new StringBuilder();
        while(i < address.length()) {
            builder.append(address.charAt(i++));
        }
        String request = builder.toString();
        System.out.println(domain);
        System.out.println(request);

        try(Socket socket = new Socket(domain, portNum)) {
            BufferedReader sokIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sokOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(!socket.isInputShutdown() && !socket.isClosed() && !(sokIn.readLine() == null)) {
                sokOut.println("GET " + request);
                sokOut.println("Host: " + domain);
                sokOut.println("Connection: close");
                sokOut.println();
                
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}