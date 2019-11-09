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

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstFlashcard(model);
        deleteFirstFlashcard(model);
        model.undoFlashcardList();
        model.undoFlashcardList();

        deleteFirstFlashcard(expectedModel);
        deleteFirstFlashcard(expectedModel);
        expectedModel.undoFlashcardList();
        expectedModel.undoFlashcardList();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoFlashcardList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoFlashcardList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
