package seedu.revision.ui.answers;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;

public class TfAnswersGridPane extends AnswersGridPane {
    private final Logger logger = LogsCenter.getLogger(TfAnswersGridPane.class);

    @FXML
    private Button option1;
    @FXML
    private Button option2;

    public TfAnswersGridPane(String fxml, Answerable answerable) {
        super(fxml, answerable);
        option1.setText("True");
        option2.setText("False");
        this.getRoot().getStyleClass().add("option-label");
    }

    public void updateAnswers(Answerable answerable) {
        option1.setText("True");
        option2.setText("False");
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TfAnswersGridPane)) {
            return false;
        }
        // state check
        TfAnswersGridPane answersGrid = (TfAnswersGridPane) other;
        return answerable.equals(answersGrid.answerable);
    }
}
