package seedu.address.appmanager.timer;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

/**
 * Represents a countdown timer that runs during a Game session.
 */
public class GameTimerManager implements GameTimer {

    private Timer timer;
    private long totalTimeGiven; // the initial time allocated for the timer.
    private long timeLeft; // the time left of this timer, updated by the timer.
    private String mainMessage;

    /** Call-back functions that this GameTimerManager is dependent on. */
    private SkipOverCallBack skipOverCallBack;
    private UpdateTimerCallBack updateTimerCallBack;
    private UpdateHintCallBack updateHintCallBack;

    private boolean cancelled = false;

    // By default no hints (and by extension by HintTimingQueue) are provided.
    private HintTimingQueue hintTimingQueue = null;

    // If no hints are enabled, nextTimeForHint is set at -100ms (placeholder for null).
    private long nextTimeForHint = -100L;

    /**
     * Creates a new GameTimerManager instance but does not run it yet.
     *
     * @param mainMessage Main message to feed back to UI.
     * @param durationInMs Total duration that this countdown timer is to run for.
     * @param skipOverCallBack Call-back function to notify that a skip-over is to be executed.
     * @param updateTimerCallBack Call-back function to notify that TimerDisplay needs to be updated.
     * @param updateHintCallBack Call-back function to notify that HintDisplay needs to be updated.
     */
    public GameTimerManager(String mainMessage, long durationInMs,
                            SkipOverCallBack skipOverCallBack,
                            UpdateTimerCallBack updateTimerCallBack,
                            UpdateHintCallBack updateHintCallBack) {
        this.mainMessage = mainMessage;
        this.skipOverCallBack = skipOverCallBack;
        this.updateTimerCallBack = updateTimerCallBack;
        this.updateHintCallBack = updateHintCallBack;
        this.totalTimeGiven = durationInMs;
        this.timeLeft = totalTimeGiven;

        /** Marks this current java.util.Timer instance as a Daemon thread to ensure JVM exits properly. */
        this.timer = new Timer(true);
    }

    /**
     * Aborts the current timer even if it has not finished running.
     */
    public void abortTimer() {
        cancelled = true;
        this.timer.cancel();
        if (!Thread.currentThread().getName().equals("JavaFX Application Thread")) {
            // Ensuring any changes to UI are always called from JavaFX Application Thread.
            Platform.runLater(() ->
                    updateTimerCallBack.updateTimerDisplay("", 0, totalTimeGiven));
            return;
        }
        updateTimerCallBack.updateTimerDisplay("", 0, totalTimeGiven);

    }

    /**
     * Starts the timer and updates the AppManager periodically via Call-backs.
     * When this method is called, it executes on the JavaFX Application Thread, but
     * it's inner run() method in TimerTask executes on a different thread.
     */
    public void run() {
        timer.schedule(new TimerTask() {
            public void run() {

                Platform.runLater(() -> {
                    /* Guard block to prevent concurrency issues. Timer.cancel() has no
                     * real time guarantee.
                     */

                    if (cancelled) {
                        return;
                    }

                    if (timeLeft >= 0) {
                        callBackToUpdateTimerDisplay();
                    }

                    // When timeLeft has reached a timeStamp to request for more hints from AppManager.
                    if (nextTimeForHint != -100L && nextTimeForHint == timeLeft) {
                        callBackToUpdateHints();
                    }

                    if (timeLeft <= 0) {
                        stopAndCallBackToSkipOver();
                        return;
                    }
                    --timeLeft;
                });
            }
        }, 0, 1); // Start timer immediately, and refresh every 1ms
    }

    /**
     * Performs a callBack to AppManager to notify that TimerDisplay needs to be updated.
     */
    private void callBackToUpdateTimerDisplay() {
        updateTimerCallBack.updateTimerDisplay(
                mainMessage + ": " + ((double) timeLeft) / 1000,
                timeLeft, totalTimeGiven);
    }

    /**
     * Stops the current TimerTask and performs a callBack to AppManager to simulate a SkipCommand.
     */
    private void stopAndCallBackToSkipOver() {
        cancelled = true;
        timer.cancel();

        // Makes a call-back to the AppManager to execute a 'skip' command
        skipOverCallBack.skipOverToNextQuestion();

    }

    /**
     * Performs a callback to AppManager requesting for more hints to be shown.
     */
    private void callBackToUpdateHints() {
        updateHintCallBack.requestHint();
        nextTimeForHint =
                hintTimingQueue.isEmpty() ? -100L : hintTimingQueue.pollNextTimeToUpdate();
    }

    /**
     * Returns the total duration that the current timer task has elapsed.
     */
    public long getElapsedMillis() {
        return totalTimeGiven >= 0 ? totalTimeGiven - timeLeft : 0;
    }

    /**
     * Initializes the queue of timestamps to request for hints based on the {@code hintFormatSize}
     * and {@code timeAllowedPerQuestion}.
     */
    public void initHintTimingQueue(int hintFormatSize, long timeAllowedPerQuestion) {
        this.hintTimingQueue = new HintTimingQueue(hintFormatSize, timeAllowedPerQuestion);
        nextTimeForHint = hintTimingQueue.pollNextTimeToUpdate();
    }

}
