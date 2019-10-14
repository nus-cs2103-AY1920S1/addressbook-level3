package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;
import static seedu.savenus.testutil.TypicalMenu.getPoorMenu;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BuyCommand}.
 */
public class BuyCommandTest {
    // Model
    private Model poorModel = new ModelManager(getPoorMenu(), new UserPrefs());

    @Test
    public void execute_notEnoughFunds_success() {

        BuyCommand buyCommand = new BuyCommand(INDEX_FIRST_FOOD);
        String expectedMessage = Messages.MESSAGE_INSUFFICIENT_FUNDS;

        assertCommandFailure(buyCommand, poorModel, expectedMessage);
    }

    @Test
    public void equals() {
        BuyCommand buyFirstCommand = new BuyCommand(INDEX_FIRST_FOOD);
        BuyCommand buySecondCommand = new BuyCommand(INDEX_SECOND_FOOD);

        // same object -> returns true
        assertTrue(buyFirstCommand.equals(buyFirstCommand));

        // same values -> returns true
        BuyCommand buyFirstCommandCopy = new BuyCommand(INDEX_FIRST_FOOD);
        assertTrue(buyFirstCommand.equals(buyFirstCommandCopy));

        // different types -> returns false
        assertFalse(buyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(buyFirstCommand.equals(null));

        // different food -> returns false
        assertFalse(buyFirstCommand.equals(buySecondCommand));
    }
}
