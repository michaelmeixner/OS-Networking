public class PoolBot extends Thread {
    private Runnable task;
    private ThreadPool pool;
    private boolean canSwim;
    private final Object SEMAPHORE;
    private static int botCounter;
    public final int botID;

    public PoolBot(ThreadPool pool) {
        this.pool = pool;
        SEMAPHORE = new Object();
        botID = ++botCounter;
    }

    public void halt() {
        synchronized(SEMAPHORE) {
            canSwim = false;
            SEMAPHORE.notifyAll();
        }
    }

    public void setTask(Runnable task) {
        synchronized(SEMAPHORE) {
            this.task = task;
            SEMAPHORE.notifyAll();
        }
    }


    public void run() {
        canSwim = true;
        Runnable workingTask = null;

        while (true) {
            synchronized (SEMAPHORE) {
                while (task == null && canSwim) {
                    try {
                        SEMAPHORE.wait();
                    } catch (InterruptedException ie) {

                    }
                }
                if(!canSwim) {
                    return;
                }
                workingTask = task;
                task = null;
            }
            workingTask.run();
            
            pool.notifyReadyForMoreWork(this);
        }
    }
}