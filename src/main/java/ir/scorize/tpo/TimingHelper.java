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
        final int cumulativeWaitTime = waitTime;
        waitTime = 0;
        tasks.add(new TimingTask(new TimerTask() {
            @Override
            public void run() {
                x.run();
                doNextTask();
            }
        }, cumulativeWaitTime));

        return this;
    }

    public TimingHelper repeat(Function<Integer, Void> x, int period, final int times) {
        final int cumulativeWaitTime = waitTime;
        waitTime = 0;
        tasks.add(new TimingTask(new TimerTask() {
            private int counter = 0;

            @Override
            public void run() {
                x.apply(counter);
                counter++;
                if (counter == times) {
                    cancel();
                    doNextTask();
                }
            }
        }, cumulativeWaitTime, period));

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
        if (task != null) {
            if (task.period <= 0) {
                // Run Once
                mTimer.schedule(task.task, task.waitBefore);
            } else {
                mTimer.scheduleAtFixedRate(task.task, task.waitBefore, task.period);
            }
        }
    }
}


class TimingTask {
    TimerTask task;
    int waitBefore;
    int period;

    public TimingTask(TimerTask task, int waitBefore) {
        this.task = task;
        this.waitBefore = waitBefore;
        this.period = -1;
    }

    public TimingTask(TimerTask task, int waitBefore, int period) {
        this.task = task;
        this.waitBefore = waitBefore;
        this.period = period;
    }
}