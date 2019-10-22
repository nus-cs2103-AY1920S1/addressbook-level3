package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    private static final String NEW_LINE_CHAR = "\n";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    public void appendFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        setFeedbackToUser(getFeedbackToUser().concat(feedbackToUser));
    }

    public void appendNewLineInFeedBackToUser() {
        appendFeedbackToUser(NEW_LINE_CHAR);
    }

    public void appendNewLineInFeedBackToUser(int n) {
        for (int i = 0; i < n; i++) {
            appendNewLineInFeedBackToUser();
        }
    }

    public String getFeedbackToUser() {
        return resultDisplay.getText();
    }

}
