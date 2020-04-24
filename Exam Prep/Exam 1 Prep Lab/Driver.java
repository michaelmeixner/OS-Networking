import java.io.*;
public class Driver {
    public static void main(String[] args) {
        File[] roots = File.listRoots();
        File root = roots[0];
        File[] directories = root.listFiles();
        Prep[] rootThread = new Prep[directories.length];
        for(int i = 0; i < directories.length; i++) {
            if(directories[i].isDirectory()) {
                rootThread[i] = new Prep(directories[i]);
                rootThread[i].start();
            }
        }
    }
}