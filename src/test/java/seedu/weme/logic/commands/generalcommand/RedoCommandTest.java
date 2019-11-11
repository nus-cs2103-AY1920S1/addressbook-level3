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

public class RedoCommandTest extends ApplicationTest {
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
        model.undoWeme();
        model.undoWeme();

        deleteFirstMeme(expectedModel);
        deleteFirstMeme(expectedModel);
        expectedModel.undoWeme();
        expectedModel.undoWeme();

        RedoCommand redoCommand = new RedoCommand();

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, expectedModel.redoWeme());

        // multiple redo states
        assertCommandSuccess(redoCommand, model, expectedMessage, expectedModel);

        expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, expectedModel.redoWeme());

        // single redo state
        assertCommandSuccess(redoCommand, model, expectedMessage, expectedModel);

        // no redo state
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
