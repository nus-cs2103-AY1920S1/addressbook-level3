package seedu.weme.logic.commands.generalcommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.deleteFirstMeme;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

public class UndoCommandTest extends ApplicationTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute() {
        deleteFirstMeme(model);
        deleteFirstMeme(model);

        deleteFirstMeme(expectedModel);
        deleteFirstMeme(expectedModel);

        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, expectedModel.undoWeme());

        // multiple undo states
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

        expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, expectedModel.undoWeme());

        // single undo states
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

        // no undo states
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
