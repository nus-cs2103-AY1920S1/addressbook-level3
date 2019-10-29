package seedu.revision.ui.answers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.ui.UiPart;

/**
 * Shows available answers/options to the user during quiz.
 */
public abstract class AnswersGridPane extends UiPart<Region> {
    public static final String MCQ_GRID_PANE_FXML = "McqAnswersGridPane.fxml";
    public static final String TF_GRID_PANE_FXML = "TfAnswersGridPane.fxml";
    public final Answerable answerable;

    @FXML
    private Button option1;
    @FXML
    private Button option2;
    @FXML
    private Button option3;
    @FXML
    private Button option4;

    /**
     * Initialises an AnswersGridPane used to display answers.
     * @param answerable the answerable that is used to display answers.
     */
    public AnswersGridPane(String fxml, Answerable answerable) {
        super(fxml);
        this.answerable = answerable;
    }

    /**
     * Updates the grid pane with updated answers.
     * @param answerable the question to be answered.
     */
    public abstract void updateAnswers(Answerable answerable);


}
