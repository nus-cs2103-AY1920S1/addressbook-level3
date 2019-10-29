package seedu.mark.logic.commands;

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

public class UndoCommandTest {

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
        deleteFirstBookmark(expectedModel);

        deleteFirstBookmark(model);
        deleteFirstBookmark(expectedModel);

        // Two undoable Mark states
        String expectedRecord1 = expectedModel.undoMark(1);
        String expectedMessage1 = String.format(UndoCommand.MESSAGE_SUCCESS, expectedRecord1);
        assertCommandSuccess(new UndoCommand(1), model, storage, expectedMessage1, expectedModel);

        // Single undoable Mark state
        String expectedRecord2 = expectedModel.undoMark(1);
        String expectedMessage2 = String.format(UndoCommand.MESSAGE_SUCCESS, expectedRecord2);
        assertCommandSuccess(new UndoCommand(1), model, storage, expectedMessage2, expectedModel);

        // No undoable Mark state
        String expectedMessage3 = String.format(UndoCommand.MESSAGE_FAILURE, 0);
        assertCommandFailure(new UndoCommand(1), model, storage, expectedMessage3);
    }

    @Test
    public void execute_multiple() {
        deleteFirstBookmark(model);
        deleteFirstBookmark(model);

        deleteFirstBookmark(expectedModel);
        deleteFirstBookmark(expectedModel);

        // Two undoable Mark states
        String expectedMessage1 = String.format(UndoCommand.MESSAGE_FAILURE, 2);
        assertCommandFailure(new UndoCommand(3), model, storage, expectedMessage1);

        // Two undoable Mark states
        String expectedRecord2 = expectedModel.undoMark(2);
        String expectedMessage2 = String.format(UndoCommand.MESSAGE_SUCCESS, expectedRecord2);
        assertCommandSuccess(new UndoCommand(2), model, storage, expectedMessage2, expectedModel);

        // No undoable Mark state
        String expectedMessage3 = String.format(UndoCommand.MESSAGE_FAILURE, 0);
        assertCommandFailure(new UndoCommand(1), model, storage, expectedMessage3);
    }
}
