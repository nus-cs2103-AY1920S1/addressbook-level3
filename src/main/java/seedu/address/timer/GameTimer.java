package seedu.address.timer;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import seedu.address.ui.TimerDisplay;

/**
 * Represents a countdown timer that runs during a Game session (if enabled).
 */
public class GameTimer {

    private Timer timer;
    private double currentMilliSeconds;
    private TimerDisplay timerDisplay;
    private String mainMessage;

    /**
     * Creates a new GameTimer instance, but does not run it yet.
     *
     * @param mainMessage String of the message intended to be shown on UI.
     * @param durationInMs Duration that the Timer runs for, in milliseconds.
     * @param timerDisplay TimerDisplay UI Object that is to be updated to show mainMessage.
     */
    public GameTimer(String mainMessage, double durationInMs, TimerDisplay timerDisplay) {
        this.mainMessage = mainMessage;
        this.timerDisplay = timerDisplay;
        this.currentMilliSeconds = durationInMs;
        this.timer = new Timer(true);
    }

    private void updateMilliSecondsLeft() {
        currentMilliSeconds--;
    }

    public double getTimeLeft() {
        return currentMilliSeconds;
    }

    /**
     * Aborts the current timer even if it has not finished running.
     */
    public void abortTimer() {
        this.timer.cancel();
        this.timer.purge();
    }

    /**
     * Starts the timer and updates the JavaFX UI periodically.
     * Runs on same thread as JavaFX UI.
     */
    public void run() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (getTimeLeft() >= 0) {
                        timerDisplay.setNormalTextColour();
                        timerDisplay.setFeedbackToUser(mainMessage + ": " + getTimeLeft() / 100);
                        if (getTimeLeft() <= 500) {
                            timerDisplay.setAlertTextColour();
                        }
                    } else {
                        timer.cancel();
                        timerDisplay.setFeedbackToUser("Time's Up!");
                    }
                    updateMilliSecondsLeft();
                });
            }
        }, 0, 10); // Start timer immediately, and refresh every 1ms
    }
}
