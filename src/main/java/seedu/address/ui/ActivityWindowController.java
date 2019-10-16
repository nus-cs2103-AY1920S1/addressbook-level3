package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seedu.address.logic.FunctionMode;

public class ActivityWindowController {

    private static final int FLASHCARD_TAB_INDEX = 1;
    private static final int CHEATSHEET_TAB_INDEX = 2;
    private static final int NOTES_TAB_INDEX = 3;

    @FXML
    private TabPane activityWindow;

    @FXML
    private Tab FlashcardTab;

    @FXML
    private Tab CheatsheetTab;

    @FXML
    private Tab NotesTab;

    @FXML
    private FlashcardTabController flashcardTabController;

    @FXML
    private CheatsheetTabController cheatsheetTabController;

    @FXML
    private NotesTabController notesTabController;

    /**
     * Switches the view of the activity window.
     * @param targetMode Function mode that user wants to switch to
     */
    public void switchWindowTo(FunctionMode targetMode) {
        switch (targetMode) {
            case FLASHCARD:
                activityWindow.getSelectionModel().select(FLASHCARD_TAB_INDEX);
                break;
            case CHEATSHEET:
                activityWindow.getSelectionModel().select(CHEATSHEET_TAB_INDEX);
                break;
            case NOTE:
                activityWindow.getSelectionModel().select(NOTES_TAB_INDEX);
                break;
            default:
        }
    }

}
