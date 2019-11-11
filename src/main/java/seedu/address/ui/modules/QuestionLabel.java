package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * UI Class that represents the region for the question to be displayed on.
 */
public class QuestionLabel extends UiPart<Region> {
    private static final String FXML = "QuestionLabel.fxml";

    @FXML
    private Label questionLabel;

    public QuestionLabel() {
        super(FXML);
        questionLabel.setWrapText(true);
    }

    public void updateQuestionLabel(String string) {
        questionLabel.setText(string);
    }

}
