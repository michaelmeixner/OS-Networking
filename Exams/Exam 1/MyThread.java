
import java.lang.Thread;
public class MyThread extends Thread implements TimedAction {
    private TimedAction action;
    private long fireSchedule;
    private boolean repeat;
    private String message = null;

    public MyThread() {}

    public String ping() {
        return message;
    }

    public void timerFired() {
        action.notifyAll();
    }

    public boolean halt() {
        return repeat = false;
    }

    public void run() {
        repeat = true;
        while(repeat) {
            try {
                sleep(1000);
                timerFired();
            } catch(InterruptedException ie) {
                ie.printStackTrace(System.err);
            }
        }
    }
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.run();
    }
}