package seedu.weme.logic.commands.generalcommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.deleteFirstMeme;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.Test;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

public class RedoCommandTest {
    private final Model model = new ModelManager(getTypicalWeme(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());

    @Test
    public void execute() {
        deleteFirstMeme(model);
        deleteFirstMeme(model);
        model.undoWeme();
        model.undoWeme();

        deleteFirstMeme(expectedModel);
        deleteFirstMeme(expectedModel);
        expectedModel.undoWeme();
        expectedModel.undoWeme();

        RedoCommand redoCommand = new RedoCommand();

        expectedModel.redoWeme();

        // multiple redo states
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redoWeme();

        // single redo state
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redo state
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
