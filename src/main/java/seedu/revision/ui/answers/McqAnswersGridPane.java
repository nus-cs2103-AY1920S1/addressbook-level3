package seedu.revision.ui.answers;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.answer.Answer;

/**
 * McqAnswersGridPane class used to display Mcq Answers.
 */
public class McqAnswersGridPane extends AnswersGridPane {
    private static final String MCQ_GRID_PANE_FXML = "McqAnswersGridPane.fxml";
    private final Logger logger = LogsCenter.getLogger(McqAnswersGridPane.class);

    @FXML
    private Button option1;
    @FXML
    private Button option2;
    @FXML
    private Button option3;
    @FXML
    private Button option4;

    public McqAnswersGridPane(Answerable answerable) {
        super(MCQ_GRID_PANE_FXML, answerable);

        updateAnswers(answerable);
        this.getRoot().getStyleClass().add("option-label");
    }

    /**
     * Updates the answers displayed during quiz mode.
     * @param answerable answerable used to update answers.
     */
    public void updateAnswers(Answerable answerable) {
        ArrayList<Answer> answerList = answerable.getCombinedAnswerList();
        option1.setText("a. " + answerList.get(0).toString());
        option2.setText("b. " + answerList.get(1).toString());
        option3.setText("c. " + answerList.get(2).toString());
        option4.setText("d. " + answerList.get(3).toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof McqAnswersGridPane)) {
            return false;
        }
        // state check
        McqAnswersGridPane answersGrid = (McqAnswersGridPane) other;
        return answerable.equals(answersGrid.answerable);
    }
}
