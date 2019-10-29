package seedu.revision.ui.answers;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;

/**
 * SaqAnswersGridPane class used to remove options used by Mcq and True False questions.
 */
public class SaqAnswersGridPane extends AnswersGridPane {
    private final Logger logger = LogsCenter.getLogger(SaqAnswersGridPane.class);

    @FXML
    private Button option1;
//    @FXML
//    private Button option2;

    /**
     * Instantiates SaqAnswersGridPane.
     * @param fxml the fxml used to display the SaqAnswersGridPane.
     * @param answerable answerable used to display answers.
     */
    public SaqAnswersGridPane(String fxml, Answerable answerable) {
        super(fxml, answerable);
        option1.setText("True");
//        option2.setText("False");
        this.getRoot().getStyleClass().add("option-label");
    }

    /**
     * Updates the panel to remove options.
     * @param answerable
     */
    public void updateAnswers(Answerable answerable) {
        option1.setText("True");
//        option2.setText("False");
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SaqAnswersGridPane)) {
            return false;
        }
        // state check
        SaqAnswersGridPane answersGrid = (SaqAnswersGridPane) other;
        return answerable.equals(answersGrid.answerable);
    }
}
