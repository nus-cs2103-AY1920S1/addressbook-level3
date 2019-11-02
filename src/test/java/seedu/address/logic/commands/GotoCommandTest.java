package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertViewContactFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;
import static seedu.address.testutil.Views.FIRST_VIEW;
import static seedu.address.testutil.Views.SECOND_VIEW;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.View;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GotoCommand}.
 */
//@@author {lawncegoh}
class GotoCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GotoCommand(null));
    }

    @Test
    public void execute_validView() {

    }

    @Test
    public void execute_invalidView() {
        View invalidView = new View("invalid", 4);
        GotoCommand gotoCommand = new GotoCommand(invalidView);
        assertViewContactFailure(gotoCommand, model, "Error in displaying View");
    }

    @Test
    public void equals() {
        GotoCommand gotoFirstCommand = new GotoCommand(FIRST_VIEW);
        GotoCommand gotoSecondCommand = new GotoCommand(SECOND_VIEW);
        // same object -> returns true
        assertTrue(gotoFirstCommand.equals(gotoFirstCommand));

        // same values -> returns true
        GotoCommand gotoFirstCommandCopy = new GotoCommand(FIRST_VIEW);
        assertEquals(gotoFirstCommand, gotoFirstCommandCopy);

        assertNotEquals(1, gotoFirstCommand);

        // different types -> returns false
        assertFalse(gotoFirstCommand.equals(1));
        // different types -> returns false;

        // null -> returns false
        assertFalse(gotoFirstCommand.equals(null));

        // different gotoCommands -> returns false
        assertFalse(gotoFirstCommand.equals(gotoSecondCommand));
    }

}
