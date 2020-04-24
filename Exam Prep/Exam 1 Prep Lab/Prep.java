import java.io.*;
public class Prep extends Thread {
    File directory;

    public Prep(File d) {
        this.directory = d;
        if(!directory.isDirectory() || !directory.exists())
            throw new IllegalArgumentException();
    }

    public void run() {
        long start, end, size = 0;
        File[] files = directory.listFiles();
        start = System.nanoTime();
        if(files != null) {
            for(File file: files)
                if(!file.isDirectory())    
                    size += file.length();
        }
        end = System.nanoTime();
        System.out.println("Directory name: " + directory.getName() + "\nSize: " + size + "\nTotal time: " + (end - start)/1E9 );
    }
}