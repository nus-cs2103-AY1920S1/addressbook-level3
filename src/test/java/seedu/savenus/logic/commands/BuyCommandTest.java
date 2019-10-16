package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Menu;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.TimeOfPurchase;
import seedu.savenus.model.wallet.Wallet;

import java.util.List;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BuyCommand}.
 */
public class BuyCommandTest {

    // Test Wallet payment
    @Test
    public void payFoodPrice_notEnoughFunds_success() {
        Wallet modelWallet = new Wallet("5", "100");
        Price expensiveFoodPrice = new Price("10");

        assertThrows(CommandException.class, () -> modelWallet.pay(expensiveFoodPrice));
    }

    // Test Wallet payment
    @Test
    public void payFoodPrice_enoughFunds_success() {
        Wallet modelWallet = new Wallet("5", "100");
        Price cheapFoodPrice = new Price("2");
        try {
            modelWallet.pay(cheapFoodPrice);
        } catch (Exception e) {
            return;
        }
        assertTrue(modelWallet.equals(new Wallet("3","100")));
    }

    // Test adding purchase to purchase history
    @Test
    public void addPurchase_success() {
        Menu testMenu1 = new Menu(getTypicalMenu());
        Menu testMenu2 = new Menu(getTypicalMenu());
        Purchase testPurchase = new Purchase(new Name("testFoodName"), new Price("2"));
        testMenu1.addPurchase(testPurchase);
        testMenu2.setPurchaseHistory(List.of(
                new Purchase(new Name("Ji Fan"), new Price("3.99"), new TimeOfPurchase("1570976664361")),
                new Purchase(new Name("Wagyu steak"), new Price("50.00"), new TimeOfPurchase("1570976665687")),
                testPurchase
        ));
        assertTrue(testMenu1.equals(testMenu2));
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
