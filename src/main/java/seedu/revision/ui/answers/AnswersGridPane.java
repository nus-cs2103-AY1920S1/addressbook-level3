package seedu.revision.ui.answers;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;
import javafx.fxml.FXML;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.ui.UiPart;

public abstract class AnswersGridPane extends UiPart<Region> {
    public static final String MCQ_GRID_PANE_FXML = "McqAnswersGridPane.fxml";
    public static final String TF_GRID_PANE_FXML = "TfAnswersGridPane.fxml";
    public final Answerable answerable;

    @FXML
    Button option1;
    @FXML
    Button option2;
    @FXML
    Button option3;
    @FXML
    Button option4;



    public AnswersGridPane(String fxml, Answerable answerable) {
        super(fxml);
        this.answerable = answerable;
    }

    public abstract void updateAnswers(Answerable answerable) ;


}
