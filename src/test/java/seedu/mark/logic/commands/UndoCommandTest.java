package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.deleteFirstBookmark;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.storage.Storage;

public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
    private Storage storage = new CommandTestUtil.StorageStub();

    @Test
    public void execute() {
        deleteFirstBookmark(model);
        deleteFirstBookmark(expectedModel);

        deleteFirstBookmark(model);
        deleteFirstBookmark(expectedModel);

        // Two undoable Mark states
        expectedModel.undoMark();
        assertCommandSuccess(new UndoCommand(), model, storage, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // Single undoable Mark state
        expectedModel.undoMark();
        assertCommandSuccess(new UndoCommand(), model, storage, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // No undoable Mark state
        assertCommandFailure(new UndoCommand(), model, storage, UndoCommand.MESSAGE_FAILURE);
    }
}
