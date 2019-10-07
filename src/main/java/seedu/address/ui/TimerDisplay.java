package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import static java.util.Objects.requireNonNull;

public class TimerDisplay extends UiPart<Region> {

    //Need to make TimerDisplay.fxml
    private static final String FXML = "TimerDisplay.fxml";

    @FXML
    private TextArea timerDisplay;

    public TimerDisplay() {
        super(FXML);
    }

    public void setAlertTextColour() {
        timerDisplay.setStyle("-fx-text-fill: #FF69B4;");
    }

    public void setNormalTextColour() {
        timerDisplay.setStyle("-fx-text-fill: #ADFF2F;");
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        timerDisplay.setText(feedbackToUser);
    }
}
