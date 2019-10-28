package seedu.revision.ui.answers;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.answer.Answer;

public class McqAnswersGridPane extends AnswersGridPane {
    private final Logger logger = LogsCenter.getLogger(McqAnswersGridPane.class);

    @FXML
    private Button option1;
    @FXML
    private Button option2;
    @FXML
    private Button option3;
    @FXML
    private Button option4;

    public McqAnswersGridPane(String fxml, Answerable answerable) {
        super(fxml, answerable);
        ArrayList<Answer> answerList = answerable.getCombinedAnswerList();
        option1.setText(answerList.get(0).toString());
        option2.setText(answerList.get(1).toString());
        option3.setText(answerList.get(2).toString());
        option4.setText(answerList.get(3).toString());
        this.getRoot().getStyleClass().add("option-label");
    }

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
        if (!(other instanceof McqAnswersGridPane)) {
            return false;
        }
        // state check
        McqAnswersGridPane answersGrid = (McqAnswersGridPane) other;
        return answerable.equals(answersGrid.answerable);
    }
}
