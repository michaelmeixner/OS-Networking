import java.rmi.registry.*;
public class Client {
    private Client() {}
    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Hello stub = (Hello) registry.lookup("write Something");
            String response = KeyboardReader.readLine("Type a message:");
            while(true)
            {
                response = stub.writeSomething(response);
                response = KeyboardReader.readLine("Response received: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}