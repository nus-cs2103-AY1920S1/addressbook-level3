package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;

import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BuyCommand}.
 */
public class BuyCommandTest {

    // Due to the nature of purchases, taking in the current time, testing will be done on the wallet and purchase
    // history separately...
    // Please refer to the wallet and purchase history tests

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
