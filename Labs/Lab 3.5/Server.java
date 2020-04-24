import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
        
public class Server implements RemoteTask {
        
    public Server() {}

    public <T> T executeTask(Task<T> t) throws RemoteException
    {
        return t.execute();
    }
    
    public String sayHello() {
        return "Hello, world!";
    }

    public String writeSomething(String message)
    {
        return KeyboardReader.readLine("Hello from the other side, broother.");
    }
        
    public static void main(String args[]) {
        
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}