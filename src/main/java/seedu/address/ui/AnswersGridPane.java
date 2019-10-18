package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.AnswerSet;
import seedu.address.model.answerable.Answerable;
import javafx.fxml.FXML;

public class AnswersGridPane extends UiPart<Region> {
    private static final String FXML = "AnswersGridPane.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswersGridPane.class);

    public final Answerable answerable;

    @FXML
    private Label option1;
    @FXML
    private Label option2;
    @FXML
    private Label option3;
    @FXML
    private Label option4;


    public AnswersGridPane(Answerable answerable) {
        super(FXML);
        this.answerable = answerable;
        Set<Answer> answerSet = answerable.getAnswerSet().getCombinedAnswerSet();
        List<Answer> answerList = new ArrayList<>(answerSet);
        option1.setText(answerList.get(0).answer);
        option2.setText(answerList.get(1).answer);
        option3.setText(answerList.get(2).answer);
        option4.setText(answerList.get(3).answer);
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
