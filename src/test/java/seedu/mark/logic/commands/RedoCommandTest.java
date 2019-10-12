package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.deleteFirstBookmark;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void execute() {
        deleteFirstBookmark(model);
        deleteFirstBookmark(model);
        model.undoMark();
        model.undoMark();

        deleteFirstBookmark(expectedModel);
        deleteFirstBookmark(expectedModel);
        expectedModel.undoMark();
        expectedModel.undoMark();

        // Two redoable Mark states
        expectedModel.redoMark();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // Single redoable Mark state
        expectedModel.redoMark();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // No redoable Mark state
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
