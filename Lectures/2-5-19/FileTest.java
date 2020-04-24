import java.io.*;

public class FileTest extends Thread {
    private static File parent;
    static {
        parent = new File("Data");
        parent.mkdir();
    }
    
    private int sid;
    public FileTest(int sid) {
        this.sid = sid;
    }
    
    private static double start;
    private static double end;
    public double getElapsedTime() {
        return end - start;
    }
    
    public void run() { // the main method for threads
        // start = System.nanoTime();
        File dataFile = new File(parent, sid + ".txt");
        PrintWriter output = null;
        try {
            output = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));
            for(int j = 0; j < 1000000; j++) {
                output.println(j);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        } finally {
            try {
                output.close();
            } catch(Exception e) {}
        }
        // end = System.nanoTime();
        // System.out.println("Process: " + sid + " Time: " + ((end - start)/ 1E9) + " seconds");
    }
    public static final void main(String[] args) {
        double start = 0, end = 0;
        start = System.nanoTime();
        FileTest[] threads = new FileTest[300];
        for(int i = 0; i < 300; i++) {
            threads[i] = new FileTest(i);
            threads[i].start();
        }
        // start is essentially the call for the run method

        for(int i = 0; i < threads.length; i++) {
            // while(threads[i].isAlive()) {
                try {
                    threads[i].join();
                    // Thread.sleep(4000);
                } catch(InterruptedException ie) {}
            // }
        }
        end = System.nanoTime();
        System.out.println("Total Time: " + ((end - start) / 1E9) + " seconds");
    }
}