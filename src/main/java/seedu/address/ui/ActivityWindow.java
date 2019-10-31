package seedu.address.ui;

import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import seedu.address.logic.FunctionMode;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;

/**
 * The UI Component responsible for displaying the windows of different features as tabs.
 */
public class ActivityWindow extends UiPart<Region> {

    private static final String FXML = "ActivityWindow.fxml";
    private static final int FLASHCARD_TAB_INDEX = 1;
    private static final int NOTES_TAB_INDEX = 2;
    private static final int CHEATSHEET_TAB_INDEX = 3;

    @FXML
    private TabPane activityWindow;

    @FXML
    private BorderPane flashcardTabWindow;

    @FXML
    private BorderPane cheatsheetTabWindow;

    @FXML
    private BorderPane notesTabWindow;

    @FXML
    private FlashcardTabWindowController flashcardTabWindowController;

    @FXML
    private CheatsheetTabWindowController cheatsheetTabWindowController;

    @FXML
    private NotesTabWindowController notesTabWindowController;

    public ActivityWindow() {
        super(FXML);
    }

    /**
     * Switches the view of the activity window.
     * @param targetMode Function mode that user wants to switch to
     */
    public void switchWindowTo(FunctionMode targetMode) {
        switch (targetMode) {
        case FLASHCARD:
            activityWindow.getSelectionModel().select(FLASHCARD_TAB_INDEX);
            break;
        case NOTE:
            activityWindow.getSelectionModel().select(NOTES_TAB_INDEX);
            break;
        case CHEATSHEET:
            activityWindow.getSelectionModel().select(CHEATSHEET_TAB_INDEX);
            break;
        default:
            assert false : "Invalid target mode";
        }
    }

    public void displayFlashcard(Flashcard flashcard) {
        flashcardTabWindowController.loadFlashcard(flashcard);
    }

    public void showAnswer() {
        flashcardTabWindowController.showFlashcardAns();
    }

    public void startTimeTrial(Optional<ArrayList<Flashcard>> deck) {
        flashcardTabWindowController.startTimeTrial(deck);
    }

    public void displayNote(Note note) {
        notesTabWindowController.loadNote(note);
    }

    public void displayCheatSheet(CheatSheet cheatSheet) {
        cheatsheetTabWindowController.loadCheatSheet(cheatSheet);
    }

    public void switchCheatSheetContent(int index) {
        cheatsheetTabWindowController.showSpecificTagContents(index);
    }
}
