package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * Represents a timer display that shows the time left in seconds as well as a Progress Bar.
 */
public class TimerDisplay extends UiPart<Region> {

    private static final String FXML = "TimerDisplay.fxml";

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextArea timerText;

    public TimerDisplay() {
        super(FXML);
        progressBar.setVisible(false);

    }

    public void setAlertTextColour() {
        timerText.setId("timer-alert-style");
        progressBar.setId("progress-bar-alert");
    }

    public void setNormalTextColour() {
        timerText.setId("timer-normal-style");
        progressBar.setId("progress-bar-normal");
    }

    public void setFeedbackToUser(String feedbackToUser) {
        timerText.setText(feedbackToUser);
    }

    /**
     * Sets the percentage progress on the progress bar. Progress is zero, progress bar is made invisible.
     * @param percentage Current progress
     */
    void setProgressBarProgress(double percentage) {
        if (percentage <= 0) {
            progressBar.setVisible(false);
            return;
        }

        progressBar.setVisible(true);
        this.progressBar.setProgress(percentage);
    }

}
