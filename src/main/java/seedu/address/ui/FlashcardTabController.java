package seedu.address.ui;

import java.awt.TextArea;

import javafx.fxml.FXML;
import seedu.address.model.flashcard.Flashcard;

public class FlashcardTabController {
    @FXML
    private TextArea qnsTextArea;

    @FXML
    private TextArea ansTextArea;

    public FlashcardTabController() {
    }

    public void loadFlashcard(Flashcard flashcard) {
        qnsTextArea.setText(flashcard.getQuestion().toString());
        ansTextArea.setVisible(false);
        ansTextArea.setText(flashcard.getAnswer().toString());
    }

    public void showFlashcardAns() {
        ansTextArea.setVisible(true);
    }
}
