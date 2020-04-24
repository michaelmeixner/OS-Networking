// Michael Meixner, Lab 6
// Find something to use as a general-purpose solution to the stop method problem.
public class Lab6 extends Thread {
    int number;
    private final Object SEMAPHORE;

    public Lab6(int number) {
        this.number = number;
        SEMAPHORE = new Object();
    }

    public int add(int x) {
        return number += x;
    }

    public void run() {
        System.out.println(add(2));
        try {
            sleep(250);
        } catch(InterruptedException ie) {}
    }

    public void safeStop() {

    }

    public static void main(String[] args) {
        Lab6 test = new Lab6(0);
        test.start();
    }

}