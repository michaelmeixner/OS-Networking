public class PoolBoy extends Thread {
    private Runnable task;
    private ThreadPool pool;

    public PoolBoy(ThreadPool pool) {
        this.pool = pool;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public void run() {
        while(true) {
            if(task != null) {
                task.run();
                task = null; // throw task away when done with it or else it will keep running
                pool.notifyReadyForMoreWork(sid);
            }
        }
    }
}