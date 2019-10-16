package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.logic.FunctionMode;
import seedu.address.model.flashcard.Flashcard;

public class ActivityWindow extends UiPart<Region> {

    private static final String FXML = "ActivityWindow.fxml";
    private static final int FLASHCARD_TAB_INDEX = 1;
    private static final int CHEATSHEET_TAB_INDEX = 2;
    private static final int NOTES_TAB_INDEX = 3;

    @FXML
    private TabPane ActivityWindow;

    @FXML
    private Tab FlashcardTab;

    @FXML
    private Tab CheatsheetTab;

    @FXML
    private TextArea qnsTextArea;

    @FXML
    private TextArea ansTextArea;

    @FXML
    private Tab NotesTab;

    @FXML
    private FlashcardTabController flashcardTabController;

    @FXML
    private CheatsheetTabController cheatsheetTabController;

    @FXML
    private NotesTabController notesTabController;

    public ActivityWindow() {
        super(FXML);
        flashcardTabController = new FlashcardTabController();
    }

    /**
     * Switches the view of the activity window.
     * @param targetMode Function mode that user wants to switch to
     */
    public void switchWindowTo(FunctionMode targetMode) {
        switch (targetMode) {
            case FLASHCARD:
                ActivityWindow.getSelectionModel().select(FLASHCARD_TAB_INDEX);
                break;
            case CHEATSHEET:
                ActivityWindow.getSelectionModel().select(CHEATSHEET_TAB_INDEX);
                break;
            case NOTE:
                ActivityWindow.getSelectionModel().select(NOTES_TAB_INDEX);
                break;
            default:
        }
    }

    public void displayFlashcard(Flashcard flashcard) {
        flashcardTabController.loadFlashcard(flashcard);
    }

}
