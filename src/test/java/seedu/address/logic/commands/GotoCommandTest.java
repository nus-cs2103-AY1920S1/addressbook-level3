package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalFinSec;
import static seedu.address.testutil.Views.FIRST_VIEW;
import static seedu.address.testutil.Views.SECOND_VIEW;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.View;




/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GotoCommand}.
 */
class GotoCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void execute_validView() {
        View validView = new View("contacts", 1);

        String expectedMessage = String.format(GotoCommand.MESSAGE_SUCCESS_CONTACTS, validView);

        ModelManager expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
        expectedModel.updateFilteredContactList(p -> true);

        GotoCommand gotoCommand = new GotoCommand(validView);
        assertCommandSuccess(gotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidView() {
        View invalidView = new View("invalid", 4);
        GotoCommand gotoCommand = new GotoCommand(invalidView);
        assertCommandFailure(gotoCommand, model, Messages.MESSAGE_INVALID_VIEW);
    }

    @Test
    public void equals() {
        GotoCommand gotoFirstCommand = new GotoCommand(FIRST_VIEW);
        GotoCommand gotoSecondCommand = new GotoCommand(SECOND_VIEW);
        // same object -> returns true
        assertTrue(gotoFirstCommand.equals(gotoFirstCommand));

        // same values -> returns true
        GotoCommand gotoFirstCommandCopy = new GotoCommand(FIRST_VIEW);
        assertTrue(gotoFirstCommand.equals(gotoFirstCommandCopy));

        // different types -> returns false
        assertFalse(gotoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gotoFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(gotoFirstCommand.equals(gotoSecondCommand));
    }

}
