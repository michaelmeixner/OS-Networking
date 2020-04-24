import java.util.*;

public class ThreadPool extends Thread {
    private List<PoolBot> queuedBots;
    private List<Runnable> chores;
    private int maxBotz;
    private boolean isDead;
    private final Object SEMAPHORE;
    private List<PoolBot> beachDemons;
    private List<PoolBot> beachUsers;
    
    public ThreadPool(int maxBotz) {
        this.maxBotz = maxBotz;
        queuedBots = new ArrayList<>();
        chores = new ArrayList<>();
        beachDemons = new ArrayList<>();
        beachUsers = new ArrayList<>();
        SEMAPHORE = new Object();
    }

    public void notifyReadyForMoreWork(PoolBot sid) {
        System.out.println(sid.botID + " is ready for more work.");
        synchronized(SEMAPHORE) {
            queuedBots.add(sid);
            SEMAPHORE.notifyAll();
        }
    }

    public void addTask(Runnable task) {
        synchronized (SEMAPHORE) {
            chores.add(task);
            SEMAPHORE.notifyAll();
        }
    }

    public void halt() {
        synchronized(SEMAPHORE) {
            isDead = true;
            SEMAPHORE.notifyAll();
        }
    }

    public void run() {
        isDead = false;
        ArrayList<Runnable> processingChores = new ArrayList<>();
        List<PoolBot> workingBots = new ArrayList<>();
        List<PoolBot> beachBots = new ArrayList<>();

        while (true) {
            synchronized(SEMAPHORE) {
                while (!isDead && (processingChores.isEmpty() || workingBots.size() >= maxBotz)) { // as long as there is no work to do or nobody to do the work, wait
                    try {
                        SEMAPHORE.wait();
                    } catch (InterruptedException ie) {

                    }
                    processingChores.addAll(chores);
                    chores.clear();
                    beachBots.addAll(queuedBots);
                    workingBots.removeAll(queuedBots);
                    queuedBots.clear();
                    // System.out.println("Bottom of while loop, processing = " + processingChores.size() + " working = " + workingBots.size());
                }
                if(isDead) {
                    for(PoolBot bot : workingBots) {
                        if(!bot.isDaemon())
                            bot.halt();
                    }
                    for(PoolBot bot : beachBots) {
                        bot.halt();
                    }
                    return;
                }
            }
            
            while(!processingChores.isEmpty() && workingBots.size() < maxBotz) { // if we have work to do
                //if we have idle workers, take one, give a task
                PoolBot bot = null;
                Runnable newTask = processingChores.remove(0);
                if(newTask instanceof InterruptableTask) {
                    if(!beachDemons.isEmpty()) {
                        synchronized(SEMAPHORE) {
                            bot = beachDemons.remove(0);
                        }
                        bot.setTask(processingChores.remove(0));
                    } else if(workingBots.size() < maxBotz) { //else, if we can create new workers, do that
                        bot = new PoolBot(this);
                        bot.start();
                        bot.setTask(processingChores.remove(0));
                    } else { // else pull out an idle worker
                        continue;
                    }
                } else {
                    if(!beachUsers.isEmpty()) { // if there is a free bot, make it do something
                        synchronized(SEMAPHORE) {
                            bot = beachUsers.remove(0);
                        }
                        bot.setTask(processingChores.remove(0));
                    } else if(workingBots.size() < maxBotz) { //else, if we can create new workers, do that
                        bot = new PoolBot(this);
                        bot.start();
                        bot.setTask(processingChores.remove(0));
                    } else { // else pull out an idle worker
                        continue;
                    }
                }
                synchronized(SEMAPHORE) {
                    workingBots.add(bot);
                }
            }
        }
    }
}