package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sorter.CustomSorter;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BuyCommand}.
 */
public class BuyCommandTest {

    // Due to the nature of purchases, taking in the current time, testing will be done on the wallet and purchase
    // history separately...
    // Please refer to the wallet and purchase history tests

    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
            new PurchaseHistory(), new CustomSorter(), new SavingsAccount());

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

    @Test
    public void execute_invalidIndex_failure() {
        BuyCommand infiniteBuyCommand = new BuyCommand(Index.fromZeroBased(Integer.MAX_VALUE));
        assertThrows(CommandException.class, () -> infiniteBuyCommand.execute(model));
    }

    @Test
    public void execute_success() throws CommandException {
        Purchase purchaseToAdd = new Purchase(CARBONARA);
        String expectedMessage = String.format(BuyCommand.MESSAGE_BUY_FOOD_SUCCESS, purchaseToAdd.toString());
        BuyCommand correctCommand = new BuyCommand(INDEX_FIRST_FOOD);
        CommandResult result = correctCommand.execute(model);
        assertEquals(result, new CommandResult(String.format(BuyCommand.MESSAGE_BUY_FOOD_SUCCESS,
            purchaseToAdd.toString())));
    }
}
