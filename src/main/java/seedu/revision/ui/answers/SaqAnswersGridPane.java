package seedu.revision.ui.answers;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;

/**
 * SaqAnswersGridPane class used to display an empty pane.
 */
public class SaqAnswersGridPane extends AnswersGridPane {
    private static final String SAQ_GRID_PANE_FXML = "SaqAnswersGridPane.fxml";
    private final Logger logger = LogsCenter.getLogger(SaqAnswersGridPane.class);

    /**
     * Instantiates SaqAnswersGridPane to display an empty pane.
     * @param answerable answerable used to display answers.
     */
    public SaqAnswersGridPane(Answerable answerable) {
        super(SAQ_GRID_PANE_FXML, answerable);
        this.getRoot().getStyleClass().add("option-label");
    }

    /**
     * Updates the answers grid pane to an empty pane
     * @param answerable taken in and will update the AnswerSet.
     */
    public void updateAnswers(Answerable answerable) {
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
