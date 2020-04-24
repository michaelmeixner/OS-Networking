import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Client extends MyThread implements TimedAction {
    private static TimedAction action;
    private static long fireSchedule = 1000;
    private static boolean repeat;
    private String message = null;

    public Client() {}

    public String ping() {
        return message;
    }

    public void timerFired() {
        action.notifyAll();
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            TimedAction stub = (TimedAction) registry.lookup("TimedAction");
            MyThread thread = new MyThread();
            thread.start();
            if(thread.isInterrupted()) {
                stub.ping();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}