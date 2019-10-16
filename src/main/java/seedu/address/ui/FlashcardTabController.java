package seedu.address.ui;

import javafx.fxml.FXML;;
import javafx.scene.control.TextArea;
import seedu.address.model.flashcard.Flashcard;

public class FlashcardTabController {

    @FXML
    private TextArea qnsTextArea;

    @FXML
    private TextArea ansTextArea;

    public void loadFlashcard(Flashcard flashcard) {
        qnsTextArea.setText(flashcard.getQuestion().toString());
        ansTextArea.setVisible(false);
        ansTextArea.setText(flashcard.getAnswer().toString());
    }

    public void showFlashcardAns() {
        ansTextArea.setVisible(true);
    }
}
