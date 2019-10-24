package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "errorResultFeedback";
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextField resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
        resultDisplay.setText(feedbackToUser);
    }

    public void setErrorFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);

        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }

        resultDisplay.setText(feedbackToUser);
    }

}
