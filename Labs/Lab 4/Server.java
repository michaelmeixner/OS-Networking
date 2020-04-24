import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
        
public class Server implements RemoteTask {

    final static String NAME = "RemoteTask";
    public Server() {}

    public <T> T executeTask(Task<T> t) throws RemoteException{
        return t.execute();
    }

    public String urlName(URL url) throws RemoteException {
        File file = new File(url.toString());
        BufferedReader reader = null;
        PrintWriter writer = null;
        StringBuilder bobTheBuilder = new StringBuilder();
        if(!file.exists()) {
            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                writer = new PrintWriter(new BufferedWriter(new FileWriter(url.toString() + ".html")));
                String data = null;
                while((data = reader.readLine()) != null) {
                    writer.println(data);
                    bobTheBuilder.append(data);
                }
                return bobTheBuilder.toString();
            } catch(FileNotFoundException fnfe) {
                fnfe.printStackTrace(System.err);
            } catch(IOException ioe) {
                ioe.printStackTrace(System.err);
            } finally {
                try {
                    reader.close();
                } catch (Exception e) {}
                try {
                    writer.close();
                } catch(Exception e) {}
            }
        } else {
            String data = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                while((data = reader.readLine()) != null) {
                    bobTheBuilder.append(data);
                }
            } catch(IOException ioe) {
                ioe.printStackTrace(System.err);
            } finally {
                try {
                    reader.close();
                } catch(Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return bobTheBuilder.toString();
    }

    public static void main(String args[]) {
        File folder = new File("E:\\Documents\\OS and Networking\\Labs\\Lab 4");
        File list[] = folder.listFiles();
        for(File file : list) {
            if(file.getName().endsWith(".html")) {
                file.delete();
            }
        }

        try {
            //Make a server
            Server obj = new Server();
            //Make a stub
            RemoteTask stub = (RemoteTask) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            //This lets its data be called from the registry by the client
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(NAME, stub);//Rebind overwrites, as opposed to bind which sets it permanently and can't loop because "writeSomething" is already bound
            System.err.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}