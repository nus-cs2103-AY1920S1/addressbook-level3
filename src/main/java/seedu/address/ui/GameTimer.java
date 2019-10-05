package seedu.address.ui;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

/**
 * Class that represents a countdown timer
 */
public class GameTimer {
    private Timer timer;
    private double currentMilliSeconds;
    private ResultDisplay resultDisplay;
    private String mainMessage;



    public GameTimer(String mainMessage, double durationInMs, ResultDisplay resultDisplay) {
        this.mainMessage = mainMessage;
        this.resultDisplay = resultDisplay;
        this.currentMilliSeconds = durationInMs;
        this.timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (getTimeLeft() >= 0) {
                        resultDisplay.setFeedbackToUser(mainMessage + " : " + getTimeLeft() / 100);
                    } else {
                        timer.cancel();
                        resultDisplay.setFeedbackToUser("");
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
