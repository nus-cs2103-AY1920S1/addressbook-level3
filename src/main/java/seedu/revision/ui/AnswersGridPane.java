package seedu.revision.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;
import javafx.fxml.FXML;
import seedu.revision.model.answerable.answer.Answer;

public class AnswersGridPane extends UiPart<Region> {
    private static final String FXML = "AnswersGridPane.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswersGridPane.class);

    public final Answerable answerable;

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
