package seedu.planner.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

//@@author ernestyyh
/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class FeedbackDisplay extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "errorResultFeedback";
    private static final String FXML = "FeedbackDisplay.fxml";

    @FXML
    private TextField feedbackDisplay;

    public FeedbackDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        feedbackDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
        feedbackDisplay.setText(feedbackToUser);
    }

    public void setErrorFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);

        ObservableList<String> styleClass = feedbackDisplay.getStyleClass();

        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }

        feedbackDisplay.setText(feedbackToUser);
    }

}
