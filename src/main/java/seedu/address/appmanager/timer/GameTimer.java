package seedu.address.appmanager.timer;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

import seedu.address.appmanager.AppManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a countdown timer that runs during a Game session (if enabled).
 */
public class GameTimer implements Runnable {

    private Timer timer;
    private long totalTimeGiven; // the initial time allocated for the timer.
    private long timeLeft; // the time left of this timer, updated by the timer.
    private String mainMessage;
    private AppManager.MainWindowExecuteCallBack mainWindowExecuteCallBack;
    private AppManager.TimerDisplayCallBack timerDisplayCallBack;
    private RequestUpdateHintCallBack requestUpdateHintCallBack;
    private boolean cancelled = false;
    // By default no hints (and by extension by HintTimingQueue) are provided.
    private HintTimingQueue hintTimingQueue = null;
    // If no hints are enabled, nextTimeForHint is set at -1 (placeholder for null).
    private long nextTimeForHint = -100L;

    /**
     * Creates a new GameTimer instance, but does not run it yet.
     *
     * @param mainMessage String of the message intended to be shown on UI.
     * @param durationInMs Duration that the Timer runs for, in milliseconds.
     * @param mainWindowExecuteCallBack call-back function to send 'skip" command back to MainWindow.
     */
    public GameTimer(String mainMessage, long durationInMs,
                     AppManager.MainWindowExecuteCallBack mainWindowExecuteCallBack,
                     AppManager.TimerDisplayCallBack timerDisplayCallBack,
                     RequestUpdateHintCallBack requestUpdateHintCallBack) {
        this.mainMessage = mainMessage;
        this.mainWindowExecuteCallBack = mainWindowExecuteCallBack;
        this.timerDisplayCallBack = timerDisplayCallBack;
        this.requestUpdateHintCallBack = requestUpdateHintCallBack;
        this.totalTimeGiven = durationInMs;
        this.timeLeft = totalTimeGiven;
        this.timer = new Timer(true);
    }

    /**
     * Creates a GameTimer without callback to update hint. Todo: Give a more detailed description
     * @param mainMessage
     * @param durationInMs
     * @param mainWindowExecuteCallBack
     * @param timerDisplayCallBack
     */
    public GameTimer(String mainMessage, long durationInMs,
                     AppManager.MainWindowExecuteCallBack mainWindowExecuteCallBack,
                     AppManager.TimerDisplayCallBack timerDisplayCallBack) {
        this.mainMessage = mainMessage;
        this.mainWindowExecuteCallBack = mainWindowExecuteCallBack;
        this.timerDisplayCallBack = timerDisplayCallBack;
        this.totalTimeGiven = durationInMs;
        this.timeLeft = totalTimeGiven;
        this.requestUpdateHintCallBack = () -> {};
        this.timer = new Timer(true);
    }

    /**
     * Aborts the current timer even if it has not finished running.
     */
    public void abortTimer() {
        cancelled = true;
        this.timer.cancel();
        timerDisplayCallBack.updateTimerDisplay("", 0, totalTimeGiven);
    }

    /**
     * Starts the timer and updates the JavaFX UI periodically.
     * Runs on same thread as JavaFX UI.
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

                    if (timeLeft < 0) {
                        stopAndCallBackToSkipOver();
                    }
                    --timeLeft;
                });
            }
        }, 0, 1); // Start timer immediately, and refresh every 1ms
    }

    /**
     * Performs a callBack to the UI to update the timer display accordingly.
     */
    private void callBackToUpdateTimerDisplay() {
        timerDisplayCallBack.updateTimerDisplay(
                mainMessage + ": " + ((double) timeLeft) / 1000,
                timeLeft, totalTimeGiven);
    }

    /**
     * Stops the current TimerTask and performs a callBack to MainWindow to simulate a skip command.
     */
    private void stopAndCallBackToSkipOver() {
        cancelled = true;
        timer.cancel();

        // Show Time is Up.
        timerDisplayCallBack.updateTimerDisplay("Time's up!",
                timeLeft, totalTimeGiven);

        // Makes a call-back to the mainWindow to execute a 'skip' command
        try {
            mainWindowExecuteCallBack.execute("skip");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs a callback to AppManager requesting for more hints to be shown.
     */
    private void callBackToUpdateHints() {
        requestUpdateHintCallBack.requestHint();
        nextTimeForHint =
                hintTimingQueue.isEmpty() ? -100L : hintTimingQueue.pollNextTimeToUpdate();
    }

    /**
     * Returns the total duration that the current timer task has elapsed.
     */
    public long getElapsedMillis() {
        return totalTimeGiven - timeLeft;
    }

    /**
     * Initializes the queue of timestamps to request for hints based on the {@code hintFormatSize}
     * and {@code timeAllowedPerQuestion}.
     */
    public void setHintTimingQueue(int hintFormatSize, long timeAllowedPerQuestion) {
        this.hintTimingQueue = new HintTimingQueue(hintFormatSize, timeAllowedPerQuestion);
        nextTimeForHint = hintTimingQueue.pollNextTimeToUpdate();
    }

    /**
     * Call-back functional interface from GameManager to MainWindow, represents the GameManager sending
     * a command to the app as though it were another user.
     */
    @FunctionalInterface
    public interface RequestUpdateHintCallBack {
        void requestHint();
    }

}
