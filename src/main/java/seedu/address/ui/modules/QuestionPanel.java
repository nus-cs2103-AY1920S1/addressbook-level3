package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Panel containing the introduction screen.
 */
public class QuestionPanel extends UiPart<Region> {
    private static final String FXML = "QuestionPanel.fxml";
    private static final String QUESTION = "Question ";

    @FXML
    private Label questionNumber;

    @FXML
    private Label description;

    public QuestionPanel(int questionNumber, String description) {
        super(FXML);
        this.questionNumber.setText(QUESTION + questionNumber + ":");
        this.description.setText(description);
    }

}
