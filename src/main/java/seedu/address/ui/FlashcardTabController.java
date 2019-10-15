package seedu.address.ui;

import java.awt.TextField;

import javafx.fxml.FXML;
import seedu.address.model.flashcard.Flashcard;

public class FlashcardTabController {
    @FXML
    private TextField qnsTextField;

    @FXML
    private TextField ansTextField;

    public FlashcardTabController() {
    }

    public void loadFlashcard(Flashcard flashcard) {
        qnsTextField.setText(flashcard.getQuestion().toString());
        ansTextField.setVisible(false);
        ansTextField.setText(flashcard.getAnswer().toString());
    }

    public void showFlashcardAns() {
        ansTextField.setVisible(true);
    }
}
