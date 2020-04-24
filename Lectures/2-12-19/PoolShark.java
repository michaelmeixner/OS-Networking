import java.util.*;

public class PoolShark implements Runnable {
    private static int numSharks;
    private int id;

    public PoolShark() {
        id = numSharks++;
    }

    public void run() {
        System.out.println("Shark #" + id + " has started.");
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            // System.out.println("Shark #" + id);
            try {
                Thread.sleep(random.nextInt(5));
            } catch (InterruptedException ie) {

            }
        }
        System.out.println("Shark #" + id + " has stopped swimming (and died).");
    }
    public static final void main(String[] args) {
        ThreadPool tp = new ThreadPool(25);
        tp.start();
        for (int i = 0; i < 1000; i++) {
            tp.addTask(new PoolShark());
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {

        }
        tp.halt();
        System.out.println("Sharks are dead.");
    }
}