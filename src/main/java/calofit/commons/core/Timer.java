package calofit.commons.core;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Wraps a {@link java.util.Timer}, to run tasks on an arbitrary {@link Executor}.
 * This is based off a monotonic clock, and so is not affected by system time changes.
 */
public class Timer {

    private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
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
        exec.scheduleAtFixedRate(() -> threadExecutor.execute(task),
            period.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Shuts down the timer and any pending tasks.
     */
    public void stop() {
        exec.shutdown();
        try {
            exec.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            exec.shutdownNow();
        }
    }
}
