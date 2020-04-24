import java.util.*;

public class ThreadPool {
    private List<PoolBoy> workingBoys;
    private List<PoolBoy> beachBoys;
    private List<Runnable> chores;
    private int maxBoyz;

    public ThreadPool(int maxBoyz) {
        this.maxBoyz = maxBoyz;
        workingBoys = new ArrayList<>();
        beachBoys = new ArrayList<>();
        chores = new ArrayList<>();
    }

    public void notifyReadyForMoreWork(PoolBoy sid) {
        workingBoys.remove(sid);
        beachBoys.add(sid);
    }

    public void addTask(Runnable task) {
        chores.add(task);
    }

    public void run() {
        while(true) {
            // if we have work to do
            // AND 
            // we either have beach boys or not at maxBoyz,
            // then hand work to boy and move to workingBoys
        }
    }
}