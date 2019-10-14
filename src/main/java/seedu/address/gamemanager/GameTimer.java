package seedu.address.gamemanager;

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
    private long currentMilliSeconds;
    private long timeLeft;
    private String mainMessage;
    private GameManager.MainWindowExecuteCallBack mainWindowExecuteCallBack;
    private GameManager.TimerDisplayCallBack timerDisplayCallBack;

    /**
     * Creates a new GameTimer instance, but does not run it yet.
     *
     * @param mainMessage String of the message intended to be shown on UI.
     * @param durationInMs Duration that the Timer runs for, in milliseconds.
     * @param mainWindowExecuteCallBack call-back function to send 'skip" command back to MainWindow.
     */
    public GameTimer(String mainMessage, long durationInMs,
                     GameManager.MainWindowExecuteCallBack mainWindowExecuteCallBack,
                     GameManager.TimerDisplayCallBack timerDisplayCallBack) {
        this.mainMessage = mainMessage;
        this.mainWindowExecuteCallBack = mainWindowExecuteCallBack;
        this.timerDisplayCallBack = timerDisplayCallBack;
        this.currentMilliSeconds = durationInMs;
        this.timeLeft = currentMilliSeconds;
        this.timer = new Timer(true);
    }


    public double getTimeLeft() {
        return currentMilliSeconds;
    }

    /**
     * Aborts the current timer even if it has not finished running.
     */
    public void abortTimer() {
        timerDisplayCallBack.updateTimerDisplay("", 0);
        this.timer.cancel();
    }

    /**
     * Starts the timer and updates the JavaFX UI periodically.
     * Runs on same thread as JavaFX UI.
     */
    public void run() {
        timer.schedule(new TimerTask() {
            private long timeLeft = currentMilliSeconds;
            private boolean cancelled = false;
            public void run() {
                Platform.runLater(() -> {
                    if (timeLeft >= 0) {
                        timerDisplayCallBack.updateTimerDisplay(
                                mainMessage + ": " + ((double) timeLeft) / 1000, timeLeft);
                    } else {
                        /* Guard block to prevent concurrency issues. Timer.cancel() has no
                        * real time guarantee.
                         */
                        if (cancelled) {
                            return;
                        }
                        cancelled = true;

                        timer.cancel();

                        // Show Time is Up.
                        timerDisplayCallBack.updateTimerDisplay("Time's up!", timeLeft);

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
        return currentMilliSeconds - timeLeft;
    }
}