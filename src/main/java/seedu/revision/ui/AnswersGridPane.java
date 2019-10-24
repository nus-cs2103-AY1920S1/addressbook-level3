package seedu.revision.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

/**
 * Shows available answers/options to the user during quiz.
 */
public class AnswersGridPane extends UiPart<Region> {

    private static final String FXML = "AnswersGridPane.fxml";
    public final Answerable answerable;
    private final Logger logger = LogsCenter.getLogger(AnswersGridPane.class);

    @FXML
    private Button option1;
    @FXML
    private Button option2;
    @FXML
    private Button option3;
    @FXML
    private Button option4;


    public AnswersGridPane(Answerable answerable) {
        super(FXML);
        this.answerable = answerable;
        ArrayList<Answer> answerList = answerable.getCombinedAnswerList();
        option1.setText(answerList.get(0).toString());
        option2.setText(answerList.get(1).toString());
        option3.setText(answerList.get(2).toString());
        option4.setText(answerList.get(3).toString());
        this.getRoot().getStyleClass().add("option-label");
    }

    /**
     * Updates the grid pane with updated answers.
     * @param answerable the question to be answered.
     */
    public void updateAnswers(Answerable answerable) {
        ArrayList<Answer> answerList = answerable.getCombinedAnswerList();
        option1.setText(answerList.get(0).toString());
        option2.setText(answerList.get(1).toString());
        option3.setText(answerList.get(2).toString());
        option4.setText(answerList.get(3).toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswersGridPane)) {
            return false;
        }
        // state check
        AnswersGridPane answersGrid = (AnswersGridPane) other;
        return answerable.equals(answersGrid.answerable);
    }
}
