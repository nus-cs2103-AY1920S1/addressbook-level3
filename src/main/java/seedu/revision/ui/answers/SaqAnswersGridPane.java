package seedu.revision.ui.answers;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;

/**
 * SaqAnswersGridPane class used to update the answers pane to display nothing.
 */
public class SaqAnswersGridPane extends AnswersGridPane {
    private static final String SAQ_GRID_PANE_FXML = "SaqAnswersGridPane.fxml";
    private final Logger logger = LogsCenter.getLogger(SaqAnswersGridPane.class);

//    @FXML
//    private Button option1;
//    @FXML
//    private Button option2;

    /**
     * Instantiates SaqAnswersGridPane to display nothing.
     * @param answerable answerable used to display answers.
     */
    public SaqAnswersGridPane(Answerable answerable) {
        super(SAQ_GRID_PANE_FXML, answerable);
//        option1.setText("True");
//        option2.setText("False");
        this.getRoot().getStyleClass().add("option-label");
    }

    /**
     * Updates the answer pane to display nothing
     * @param answerable
     */
    public void updateAnswers(Answerable answerable) {
//        option1.setText("True");
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
