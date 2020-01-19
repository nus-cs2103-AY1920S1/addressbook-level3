package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.deleteFirstBookmark;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

public class RedoCommandTest {

    private Model model;
    private Model expectedModel;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMark(), new UserPrefs());
        expectedModel = new ModelManager(model.getMark(), new UserPrefs());
        storage = new StorageStub();
    }

    @Test
    public void execute_single() {
        deleteFirstBookmark(model);
        deleteFirstBookmark(model);
        model.undoMark(1);
        model.undoMark(1);

        deleteFirstBookmark(expectedModel);
        deleteFirstBookmark(expectedModel);
        expectedModel.undoMark(1);
        expectedModel.undoMark(1);

        // Two redoable Mark states
        String expectedRecord1 = expectedModel.redoMark(1);
        String expectedMessage1 = String.format(RedoCommand.MESSAGE_SUCCESS, expectedRecord1);
        assertCommandSuccess(new RedoCommand(1), model, storage, expectedMessage1, expectedModel);

        // Single redoable Mark state
        String expectedRecord2 = expectedModel.redoMark(1);
        String expectedMessage2 = String.format(RedoCommand.MESSAGE_SUCCESS, expectedRecord2);
        assertCommandSuccess(new RedoCommand(1), model, storage, expectedMessage2, expectedModel);

        // No redoable Mark state
        String expectedMessage3 = String.format(RedoCommand.MESSAGE_FAILURE, 0);
        assertCommandFailure(new RedoCommand(1), model, storage, expectedMessage3);
    }

    @Test
    public void execute_multiple() {
        deleteFirstBookmark(model);
        deleteFirstBookmark(model);
        model.undoMark(2);

        deleteFirstBookmark(expectedModel);
        deleteFirstBookmark(expectedModel);
        expectedModel.undoMark(2);

        // Two redoable Mark states
        String expectedMessage1 = String.format(RedoCommand.MESSAGE_FAILURE, 2);
        assertCommandFailure(new RedoCommand(3), model, storage, expectedMessage1);

        // Two redoable Mark states
        String expectedRecord2 = expectedModel.redoMark(2);
        String expectedMessage2 = String.format(RedoCommand.MESSAGE_SUCCESS, expectedRecord2);
        assertCommandSuccess(new RedoCommand(2), model, storage, expectedMessage2, expectedModel);

        // No redoable Mark state
        String expectedMessage3 = String.format(RedoCommand.MESSAGE_FAILURE, 0);
        assertCommandFailure(new RedoCommand(1), model, storage, expectedMessage3);
    }

    @Test
    public void equals() {
        RedoCommand redoSingle = new RedoCommand(1);
        RedoCommand redoMultiple = new RedoCommand(3);

        // same object -> returns true
        assertTrue(redoSingle.equals(redoSingle));

        // same values -> returns true
        RedoCommand redoSingleCopy = new RedoCommand(1);
        assertTrue(redoSingle.equals(redoSingleCopy));

        // different types -> returns false
        assertFalse(redoSingle.equals(1));

        // null -> returns false
        assertFalse(redoSingle.equals(null));

        // different steps -> returns false
        assertFalse(redoSingle.equals(redoMultiple));
    }
}
