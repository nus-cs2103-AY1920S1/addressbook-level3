package calofit.commons.core;

import java.time.Duration;
import java.util.TimerTask;
import java.util.concurrent.Executor;

/**
 * Wraps a {@link java.util.Timer}, to run tasks on an arbitrary {@link Executor}.
 */
public class Timer {

    private java.util.Timer bgTimer = new java.util.Timer("bgTimer", true);
    private final Executor threadExecutor;

    public Timer(Executor threadExecutor) {
        this.threadExecutor = threadExecutor;
    }

    /**
     * Schedule a periodic task.
     * @param period Task period
     * @param task Task function
     */
    public void registerPeriodic(Duration period, Runnable task) {
        bgTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                threadExecutor.execute(task);
            }
        }, period.toMillis(), period.toMillis());
    }
}
