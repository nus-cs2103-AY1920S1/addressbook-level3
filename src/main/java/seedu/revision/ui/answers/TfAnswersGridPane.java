package seedu.revision.ui.answers;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;

/**
 * TfAnswersGridPane class used to display True and False answers.
 */
public class TfAnswersGridPane extends AnswersGridPane {
    private static final String TF_GRID_PANE_FXML = "TfAnswersGridPane.fxml";
    private final Logger logger = LogsCenter.getLogger(TfAnswersGridPane.class);

    @FXML
    private Button option1;
    @FXML
    private Button option2;

    /**
     * Instantiates TfAnswersGridPane to display True and False answers.
     * @param answerable answerable used to display answers.
     */
    public TfAnswersGridPane(Answerable answerable) {
        super(TF_GRID_PANE_FXML, answerable);
        option1.setText("True");
        option2.setText("False");
        this.getRoot().getStyleClass().add("option-label");
    }

    /**
     * Updates the answers to true and false.
     * @param answerable taken in and will update the AnswerSet.
     */
    public void updateAnswers(Answerable answerable) {
        option1.setText("True");
        option2.setText("False");
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TfAnswersGridPane)) {
            return false;
        }
        // state check
        TfAnswersGridPane answersGrid = (TfAnswersGridPane) other;
        return answerable.equals(answersGrid.answerable);
    }
}
