package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    @FXML
    private TextFlow resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }


    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        Text text = new Text(feedbackToUser);
        text.setId("resulttext");
        resultDisplay.getChildren().clear();
        resultDisplay.getChildren().add(text);
    }

}
