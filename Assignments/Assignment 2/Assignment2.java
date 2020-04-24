// Michael Meixner, Assignment 2
import java.io.*;
import java.util.Arrays;
public class Assignment2 extends Thread {
    File dir = null;
    static String directory = "WorkingDir";
    public Assignment2() {
        this.dir = null;
    }
    public Assignment2(String dirName) {
        this.dir = new File(dirName);
        if(!dir.exists()) {
            System.err.println(this.dir.mkdir());
        }
    }
    public void run() {
        File[] first = dir.listFiles();
        while(true) {
            File[] second = dir.listFiles();
            if(!Arrays.equals(first, second)) {
                System.out.println("Directory Changed.");
                for(File file: second) {
                    System.out.println(file.getName());
                }
                first = dir.listFiles();
            }
            try {
                sleep(1000);
            } catch(InterruptedException ie) {

            }
        }
    }
}