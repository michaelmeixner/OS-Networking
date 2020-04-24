import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;       
public class Server implements Hello {
    public Server() {}
    //Send Hello World to client
    public String sayHello() {
        return "Hello, world!";
    }
    //Send this back and forth
    public String writeSomething(String message){
        return KeyboardReader.readLine(message);
    }   
    public static void main(String args[]) {
        try {
            //Make a server
            Server obj = new Server();
            //Make a stub
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
            // rebind the remote object's stub in the registry
            //This lets its data be called from the registry from the client
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("writeSomething", stub);//Rebind overwrites, as opposed to bind which sets it permanently and can't loop because "writeSomething" is already bound
            System.err.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}