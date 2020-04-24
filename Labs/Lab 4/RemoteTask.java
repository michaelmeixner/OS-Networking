import java.net.URL;
import java.rmi.*;

public interface RemoteTask extends Remote {
    final String NAME = "RemoteTask";
    <T> T executeTask(Task<T> t) throws RemoteException;
    public String urlName(URL url) throws RemoteException;
}