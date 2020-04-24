import java.rmi.*;
public interface MyInterface extends Remote {
    long getDriveSize() throws RemoteException;
}