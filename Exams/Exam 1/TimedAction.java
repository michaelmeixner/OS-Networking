import java.rmi.*;
public interface TimedAction extends Remote {
    public void timerFired() throws RemoteException;
    public String ping();
}