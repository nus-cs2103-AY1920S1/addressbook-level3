package seedu.revision.ui.answers;

import javafx.scene.layout.Region;

import seedu.revision.model.answerable.Answerable;
import seedu.revision.ui.UiPart;

/**
 * Shows available answers/options to the user during quiz.
 */
public abstract class AnswersGridPane extends UiPart<Region> {
    public final Answerable answerable;

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
