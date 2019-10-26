package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.Views;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GotoCommand}.
 */
class GotoCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    //    @Test
    //    public void execute_validView() {
    //        View validView = new View("contacts", 1);
    //
    //        String expectedMessage = String.format(GotoCommand.MESSAGE_SUCCESS_CONTACTS, validView);
    //
    //        ModelManager expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
    //        expectedModel.updateFilteredContactList(p -> true);
    //
    //        GotoCommand gotoCommand = new GotoCommand(validView);
    //        assertCommandSuccess(gotoCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_invalidView() {
    //        View invalidView = new View("invalid", 4);
    //        try {
    //            if (invalidView == null) {
    //                throw new CommandException("Invalid View");
    //            }
    //            GotoCommand gotoCommand = new GotoCommand(invalidView);
    //            assertCommandFailure(gotoCommand, model, Messages.MESSAGE_INVALID_VIEW);
    //        } catch (CommandException e) {
    //            System.out.println();
    //        }
    //    }

    @Test
    public void equals() {
        GotoCommand gotoFirstCommand = new GotoCommand(Views.getFirstView());
        GotoCommand gotoSecondCommand = new GotoCommand(Views.getSecondView());
        // same object -> returns true
        assertTrue(gotoFirstCommand.equals(gotoFirstCommand));

        // same values -> returns true
        GotoCommand gotoFirstCommandCopy = new GotoCommand(Views.getFirstView());
        assertEquals(gotoFirstCommand, gotoFirstCommandCopy);

        // different types -> returns false
        assertFalse(gotoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gotoFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(gotoFirstCommand.equals(gotoSecondCommand));
    }

}
