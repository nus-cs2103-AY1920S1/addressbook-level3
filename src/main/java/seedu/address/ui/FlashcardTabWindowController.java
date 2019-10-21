package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import seedu.address.model.flashcard.Flashcard;

/**
 * Controller class that handles what happens within the Flashcard Tab within the Activity Window.
 */
public class FlashcardTabWindowController {

    @FXML
    private TextArea qnsTextArea;

    @FXML
    private TextArea ansTextArea;

    /**
     * Displays the question of the flashcard specified in the flashcard tab window.
     * @param flashcard flashcard to be displayed
     */
    public void loadFlashcard(Flashcard flashcard) {
        qnsTextArea.setText(flashcard.getQuestion().toString());
        ansTextArea.setVisible(false);
        ansTextArea.setText(flashcard.getAnswer().toString());
    }

    public void showFlashcardAns() {
        ansTextArea.setVisible(true);
    }
}
