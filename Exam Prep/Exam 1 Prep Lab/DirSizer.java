import java.io.*;

public class DirSizer extends Thread {
    private File directory;
    private long dirLength;
    private final Object SEMAPHORE;

    public DirSizer(File directory) {
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
        long totalLength = 0;
        File[] allFiles = directory.listFiles(new FileFilter(){
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        DirSizer[] subDirectories = new DirSizer[allFiles.length];
        for(int i = 0; i < allFiles.length; i++) {
            subDirectories[i] = new DirSizer(allFiles[i]);
            subDirectories[i].start();
        }

        if(allFiles != null) {
            for(File file: allFiles) {
                totalLength += file.length();
            }
        }

        for(DirSizer subDir: subDirectories) {
            try {
                subDir.join();
            } catch(InterruptedException ie) {

            }
            totalLength += subDir.getLength();
        }

        synchronized(SEMAPHORE) {
            dirLength = totalLength;
        }
    }
}