package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.logic.commands.CommandTestUtil.deleteFirstFlashcard;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstFlashcard(model);
        deleteFirstFlashcard(model);

        deleteFirstFlashcard(expectedModel);
        deleteFirstFlashcard(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoFlashcardList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoFlashcardList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
