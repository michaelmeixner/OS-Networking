import java.io.*;
import java.util.*;

public class DirSizerII extends ThreadPool {
    private File directory;
    private long dirLength;
    private final Object SEMAPHORE;
    private static ThreadPool threadPool;
    public DirSizerII(File directory) {
        if(!directory.exists() || directory.isFile()) {
            throw new IllegalArgumentException(directory + " is not an existing directory.");
        }
        this.directory = directory;
        SEMAPHORE = new Object();
    }
    public long getLength() {
        synchronized(SEMAPHORE) {
            return dirLength;
        }
    }
    public final void run() {
        Stack<File> stack = new Stack<>();
        stack.push(directory);
        while(!stack.isEmpty()) {
            File dir = stack.pop();
            File[] subDirectories = directory.listFiles(new FileFilter(){
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });
            for(File subdir: subDirectories) {
                stack.push(subdir);
            }
            threadPool.addDirectory(dir);
        }
    }
}