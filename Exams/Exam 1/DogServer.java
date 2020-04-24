import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
public class DogServer implements TimedAction {
    private TimedAction action;
    private long fireSchedule;
    private boolean repeat;

    public void timerFired() {
        action.notifyAll();
    }

    public String ping() {
        Random random = new Random();
        int number = random.nextInt(6);
        String message = null;
        switch(number) {
            case 0:
                message = "How's it going?";
                break;
            case 1:
                message = "Do you have food?";
                break;
            case 2:
                message = "Are you eating?";
                break;
            case 3:
                message = "Did I hear you making food?";
                break;
            case 4:
                message = "Can we go for another walk?";
                break;
            case 5:
                message = "My food bowl is empty.";
                break;
        }
        return message;
    }

    public static void main(String[] args) {
        try {
            DogServer obj = new DogServer();
            TimedAction stub = (TimedAction) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("TimedAction", stub);
            System.out.println("Server hungry.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}