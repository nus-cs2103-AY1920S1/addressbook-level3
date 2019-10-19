package seedu.deliverymans.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that displays statistics.
 */
public class StatisticsDisplay extends UiPart<Region> {

    private static final String FXML = "StatisticsDisplay.fxml";

    @FXML
    private TextArea statisticsDisplay;

    public StatisticsDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        statisticsDisplay.setText(feedbackToUser);
    }

    public void setColor() {
        statisticsDisplay.setStyle("-fx-background-color: #963");
    }
}
