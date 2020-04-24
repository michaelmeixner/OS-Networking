import java.rmi.registry.*;
public class Client {
    private Client() {}
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            MyInterface stub = (MyInterface) registry.lookup("MyInterface");
            long size = stub.getDriveSize();
            System.out.println("Total size of top-level directories: " + size);
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }
}