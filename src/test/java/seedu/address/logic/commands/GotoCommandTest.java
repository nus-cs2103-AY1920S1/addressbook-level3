package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;
import static seedu.address.testutil.Views.FIRST_VIEW;
import static seedu.address.testutil.Views.SECOND_VIEW;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.View;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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
        View invalidView;
        try {
            invalidView = new View("invalid", 4);
        } catch (IllegalArgumentException e) {
            invalidView = null;
            View finalInvalidView = invalidView;
            assertThrows(NullPointerException.class, () -> new GotoCommand(finalInvalidView));
        }
    }

    @Test
    public void equalsForEqualOnes() {
        GotoCommand gotoFirstCommand = new GotoCommand(FIRST_VIEW);
        // same object -> returns true
        assertTrue(gotoFirstCommand.equals(gotoFirstCommand));

        // same values -> returns true
        GotoCommand gotoFirstCommandCopy = new GotoCommand(FIRST_VIEW);
        assertEquals(gotoFirstCommand, gotoFirstCommandCopy);
    }
    @Test
    public void equalsForNotEqualOnes() {
        GotoCommand gotoFirstCommand = new GotoCommand(FIRST_VIEW);
        GotoCommand gotoSecondCommand = new GotoCommand(SECOND_VIEW);

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
