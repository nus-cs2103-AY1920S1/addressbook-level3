package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.deleteFirstMeme;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.Test;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

public class RedoCommandTest {
    private final Model model = new ModelManager(getTypicalMemeBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalMemeBook(), new UserPrefs());

    @Test
    public void execute() {
        deleteFirstMeme(model);
        deleteFirstMeme(model);
        model.undoMemeBook();
        model.undoMemeBook();

        deleteFirstMeme(expectedModel);
        deleteFirstMeme(expectedModel);
        expectedModel.undoMemeBook();
        expectedModel.undoMemeBook();

        RedoCommand redoCommand = new RedoCommand();

        expectedModel.redoMemeBook();

        // multiple redo states
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redoMemeBook();

        // single redo state
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redo state
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
