package seedu.sugarmummy.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    private static final String NEW_LINE_CHAR = "\n";

    @FXML
    private Label resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Concatenates the given String to the end of this feedback.
     *
     * @param feedbackToUser String to be concantenated to the end of this feedback
     */
    public void appendFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        setFeedbackToUser(getFeedbackToUser().concat(feedbackToUser));
    }

    /**
     * Adds a new line to this feedback.
     */
    public void appendNewLineInFeedBackToUser() {
        appendFeedbackToUser(NEW_LINE_CHAR);
    }

    /**
     * Adds a given number new lines to this feedback;
     *
     * @param n Integer number representing number of lines to be appended.
     */
    public void appendNewLineInFeedBackToUser(int n) {
        for (int i = 0; i < n; i++) {
            appendNewLineInFeedBackToUser();
        }
    }

    public String getFeedbackToUser() {
        return resultDisplay.getText();
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
