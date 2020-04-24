import java.util.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ExecutorDemo { // BUILT-IN THREADPOOL STUFF; Directory stuff was a random aside (still know it though)
    public static void main(String[] args) throws Exception {
        File newFile = new File("../..", "something.txt"); // "../.." means make it in parent directory; "./." means make it in current directory
        System.out.println("Absolute Path: " + newFile.getAbsolutePath());
        System.out.println("Canonical Path: " + newFile.getCanonicalPath());
        System.out.println("Name: " + newFile.getName());
        System.out.println("Parent: " + newFile.getParent());
        System.out.println();
        
        File newCanonicalFile = newFile.getCanonicalFile();
        System.out.println("Absolute Path: " + newCanonicalFile.getAbsolutePath());
        System.out.println("Canonical Path: " + newCanonicalFile.getCanonicalPath());
        System.out.println("Name: " + newCanonicalFile.getName());
        System.out.println("Parent: " + newCanonicalFile.getParent());
        System.out.println();

        final File ROOT = new File(".").getCanonicalFile(); // navigating to root directory
        boolean isInRoot = false;
        for(File parent = newCanonicalFile.getParentFile(); parent != null; parent = parent.getParentFile()) {
            if(parent.equals(ROOT)) {
                isInRoot = true;
                break;
            }
        }
        System.out.println("In root? " + isInRoot);

        Random random = new Random();
        ExecutorService pool = Executors.newFixedThreadPool(1000000);
        while(true) {
            pool.execute(new Worker());
            try {
                Thread.sleep(random.nextInt(400) + 600);
            } catch(InterruptedException ie) {
                ie.printStackTrace(System.err);
            }
        }
    }
}
class Worker implements Runnable {
    Worker() {

    }

    public final void run() {
        // do work here
        System.out.println((char)7);
    }
}