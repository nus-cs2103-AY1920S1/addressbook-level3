package seedu.address.appmanager;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

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
    private RequestHintCallBack requestHintCallBack;
    private boolean cancelled = false;

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
                     RequestHintCallBack requestHintCallBack) {
        this.mainMessage = mainMessage;
        this.mainWindowExecuteCallBack = mainWindowExecuteCallBack;
        this.timerDisplayCallBack = timerDisplayCallBack;
        this.requestHintCallBack = requestHintCallBack;
        this.totalTimeGiven = durationInMs;
        this.timeLeft = totalTimeGiven;
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
                        timerDisplayCallBack.updateTimerDisplay(
                                mainMessage + ": " + ((double) timeLeft) / 1000,
                                timeLeft, totalTimeGiven);
                    }

                    if (timeLeft == (totalTimeGiven * 1.0) / 2) {
                        requestHintCallBack.requestHint();
                    }

                    if (timeLeft < 0) {
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
                    --timeLeft;
                });
            }
        }, 0, 1); // Start timer immediately, and refresh every 1ms
    }

    public long getElapsedMillis() {
        return totalTimeGiven - timeLeft;
    }

    /**
     * Call-back functional interface from GameManager to MainWindow, represents the GameManager sending
     * a command to the app as though it were another user.
     */
    @FunctionalInterface
    public interface RequestHintCallBack {
        void requestHint();
    }

}
