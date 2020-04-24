import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
public class MyServer implements MyInterface {
    public long getDriveSize() throws RemoteException {
        long size = 0;
        System.out.println("Server was called.");
        File[] roots = File.listRoots();
        for(int i = 0; (roots != null) && i < roots.length; i++) {
            File[] subroots = roots[i].listFiles();
            for(int j = 0; (subroots != null) && j < subroots.length; j++) {
                File[] subsubroots = subroots[j].listFiles();
                for(int k = 0; (subsubroots != null) && k < subsubroots.length; k++) {
                    size += subsubroots[k].length();
                }
            }
        }
        return size;
    }
    public static void main(String[] args) {
        try {
            MyServer server = new MyServer();
            MyInterface stub = (MyInterface) UnicastRemoteObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("MyInterface", stub);
            System.out.println("Server Ready.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}