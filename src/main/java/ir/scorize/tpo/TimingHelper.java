package ir.scorize.tpo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

/**
 * Created by mjafar on 9/29/17.
 */
public class TimingHelper {
    private Timer mTimer;
    private Queue<TimingTask> tasks = new LinkedList<>();
    private int waitTime = 0;

    public static TimingHelper create() {
        return new TimingHelper();
    }

    private TimingHelper() {
        mTimer = new Timer();
    }

    public TimingHelper wait(int seconds) {
        waitTime += seconds;
        return this;
    }

    public TimingHelper runOnce(Runnable x) {
        return then(x);
    }

    public TimingHelper then(Runnable x) {
        tasks.add(new OneTimeTimingTask(x, waitTime));
        waitTime = 0;

        return this;
    }

    public TimingHelper then(TimingHelper append) {
        waitTime += append.waitTime;
        tasks.addAll(append.tasks);

        return this;
    }

    public TimingHelper repeat(final Function<Integer, Void> x, int period, final int times) {
        final int cumulativeWaitTime = waitTime;
        waitTime = 0;
        tasks.add(new PeriodicTimingTask(x, cumulativeWaitTime, period, times));

        return this;
    }

    private boolean mStarted = false;

    public synchronized void start() {
        if (mStarted) {
            throw new IllegalStateException();
        }
        mStarted = true;
        doNextTask();
    }

    private void doNextTask() {
        TimingTask task = tasks.poll();
        System.out.println("Remaining tasks: " + tasks.size());
        if (task != null) {
            System.out.println("Scheduling " + task);
            if (task instanceof OneTimeTimingTask) {
                mTimer.schedule(task.toTimerTask(this::doNextTask), task.getWaitBefore());
            } else if (task instanceof PeriodicTimingTask) {
                mTimer.scheduleAtFixedRate(task.toTimerTask(this::doNextTask), task.getWaitBefore(), ((PeriodicTimingTask) task).getPeriod());
            }
        } else {
            System.out.println("Finished. Destroying timer");
            mTimer.cancel();
        }
    }
}


abstract class TimingTask {
    protected int waitBefore;

    protected TimingTask(int waitBefore) {
        this.waitBefore = waitBefore;
    }

    public int getWaitBefore() {
        return waitBefore;
    }

    public TimerTask toTimerTask() {
        return toTimerTask(null);
    }

    public abstract TimerTask toTimerTask(Runnable callback);
}

class OneTimeTimingTask extends TimingTask {
    private Runnable task;

    public OneTimeTimingTask(Runnable task, int waitBefore) {
        super(waitBefore);
        this.task = task;
    }

    public TimerTask toTimerTask(Runnable callback) {
        return new TimerTask() {
            @Override
            public void run() {
                task.run();
                if (callback != null)
                    callback.run();
            }
        };
    }
}

class PeriodicTimingTask extends TimingTask {
    private Function<Integer, Void> task;
    private int period;
    private int times;

    public PeriodicTimingTask(Function<Integer, Void> task, int waitBefore, int period, int times) {
        super(waitBefore);
        this.task = task;
        this.period = period;
        this.times = times;
    }

    public TimerTask toTimerTask(Runnable callback) {
        return new TimerTask() {
            private int counter = 0;

            @Override
            public void run() {
                task.apply(counter);
                counter++;
                if (counter == times) {
                    cancel();
                    if (callback != null)
                        callback.run();
                }
            }
        };
    }

    public int getPeriod() {
        return period;
    }
}