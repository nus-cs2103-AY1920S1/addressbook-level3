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
    private long totalTimeGiven; // the initial time allocated for the timer.
    private long timeLeft; // the time left of this timer, updated by the timer.
    private String mainMessage;
    private GameManager.MainWindowExecuteCallBack mainWindowExecuteCallBack;
    private GameManager.TimerDisplayCallBack timerDisplayCallBack;
    private GameManager.ResultDisplayCallBack resultDisplayCallBack;
    private boolean cancelled = false;

    /**
     * Creates a new GameTimer instance, but does not run it yet.
     *
     * @param mainMessage String of the message intended to be shown on UI.
     * @param durationInMs Duration that the Timer runs for, in milliseconds.
     * @param mainWindowExecuteCallBack call-back function to send 'skip" command back to MainWindow.
     */
    public GameTimer(String mainMessage, long durationInMs,
                     GameManager.MainWindowExecuteCallBack mainWindowExecuteCallBack,
                     GameManager.TimerDisplayCallBack timerDisplayCallBack,
                     GameManager.ResultDisplayCallBack resultDisplayCallBack) {
        this.mainMessage = mainMessage;
        this.mainWindowExecuteCallBack = mainWindowExecuteCallBack;
        this.timerDisplayCallBack = timerDisplayCallBack;
        this.resultDisplayCallBack = resultDisplayCallBack;
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
                    } else {
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

}
