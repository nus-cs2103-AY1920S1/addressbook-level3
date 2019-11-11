package seedu.address.appmanager.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.application.Platform;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents the back-end countdown timer that runs during a Game session.
 *
 * @@author kohyida1997
 */
public class GameTimerManager implements GameTimer {

    private final Logger logger = LogsCenter.getLogger(GameTimerManager.class);

    /** Internal timer that schedules TimerTasks.*/
    private Timer timer;

    /** Total time this GameTimer is allowed to run for.*/
    private long totalTimeGiven; // the initial time allocated for the timer.

    /** Time remaining for this GameTimer to run.*/
    private long timeLeft; // the time left of this timer, updated by the timer.

    /** Main message that this GameTimer will update UI components with.*/
    private String mainMessage;

    /** Call-back functions that this GameTimerManager is dependent on.*/
    private SkipOverCallBack skipOverCallBack;
    private UpdateTimerCallBack updateTimerCallBack;
    private UpdateHintCallBack updateHintCallBack;

    /** Boolean to indicated whether this GameTimer has finished running.*/
    private boolean cancelled = false;

    /** Queue of timestamps for Hints, is initialized to be null by default (not enabled).*/
    private HintTimingQueue hintTimingQueue = null;

    /** Next timing for a Hint to be requested, initialized to -100ms (represents a null value).*/
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
    GameTimerManager(String mainMessage, long durationInMs,
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


        /** Ensuring any changes to UI are always called from JavaFX Application Thread.*/
        if (!Thread.currentThread().getName().equals("JavaFX Application Thread")) {
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
                        timeLeft = timeLeft - 50;
                        return;
                    }

                    if (timeLeft <= 0) {
                        stopAndCallBackToSkipOver();
                        return;
                    }
                    timeLeft = timeLeft - 50;
                });
            }
        }, 0, 50); // Start timer immediately, and refresh every 50ms
    }

    /**
     * Performs a callBack to AppManager to notify that TimerDisplay needs to be updated.
     */
    private void callBackToUpdateTimerDisplay() {
        String timeString = String.format("%s: %.1f", mainMessage, timeLeft / 1000.0);
        updateTimerCallBack.updateTimerDisplay(timeString, timeLeft, totalTimeGiven);
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
