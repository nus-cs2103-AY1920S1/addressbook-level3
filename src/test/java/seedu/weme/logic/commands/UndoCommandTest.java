package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.deleteFirstMeme;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.Test;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

public class UndoCommandTest {
    private final Model model = new ModelManager(getTypicalMemeBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalMemeBook(), new UserPrefs());

    @Test
    public void execute() {
        deleteFirstMeme(model);
        deleteFirstMeme(model);

        deleteFirstMeme(expectedModel);
        deleteFirstMeme(expectedModel);

        UndoCommand undoCommand = new UndoCommand();

        expectedModel.undoMemeBook();

        // multiple undo states
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.undoMemeBook();

        // single undo states
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undo states
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
