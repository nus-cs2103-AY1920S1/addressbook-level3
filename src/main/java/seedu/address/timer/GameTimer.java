package seedu.address.timer;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import seedu.address.ui.TimerDisplay;

/**
 * Class that represents a countdown timer
 */
public class GameTimer {
    private Timer timer;
    private double currentMilliSeconds;
    private TimerDisplay timerDisplay;
    private String mainMessage;



    public GameTimer(String mainMessage, double durationInMs, TimerDisplay timerDisplay) {
        this.mainMessage = mainMessage;
        this.timerDisplay = timerDisplay;
        this.currentMilliSeconds = durationInMs;
        this.timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (getTimeLeft() >= 0) {
                        timerDisplay.setNormalTextColour();
                        timerDisplay.setFeedbackToUser(mainMessage + ": " + getTimeLeft() / 100);
                        if(getTimeLeft() <= 500)
                            timerDisplay.setAlertTextColour();
                    } else {
                        timer.cancel();
                        timerDisplay.setFeedbackToUser("Time's Up!");
                    }
                    updateMilliSecondsLeft();
                });
            }
        }, 0, 10); // Start timer immediately, and refresh every 1ms
    }

    private void updateMilliSecondsLeft() {
        currentMilliSeconds--;
    }

    public double getTimeLeft() {
        return currentMilliSeconds;
    }

    public void abortTimer() {
        this.timer.cancel();
        this.timer.purge();
    }
}
