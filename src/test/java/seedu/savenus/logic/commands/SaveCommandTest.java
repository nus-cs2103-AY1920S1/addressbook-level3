package seedu.savenus.logic.commands;

import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;
import static seedu.savenus.testutil.TypicalWallet.getTypicalWallet;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.CurrentSavings;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.savings.SavingsHistory;
import seedu.savenus.model.savings.exceptions.SavingsOutOfBoundException;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.util.Money;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;

/**
 * Contains integration tests (interaction with the model) and unit tests for
 * {@code SaveCommand}
 */
public class SaveCommandTest {

    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
            new PurchaseHistory(), getTypicalWallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(),
            new AliasList());

    @Test
    public void execute_validSavingsAmount_success() {
        String time = "1545739200";
        CurrentSavings testCurrentSavings = new CurrentSavings(new Money("10.00"));
        SaveCommand saveCommand = new SaveCommand("10.00", time);
        Savings testSavings = new Savings("10.00", time, false);
        String expectedMessage = String.format(SaveCommand.MESSAGE_SAVINGS_SUCCESS, testCurrentSavings.toString());
        ModelManager expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), model.getWallet(), new CustomSorter(),
                new SavingsHistory(),
                new SavingsAccount(),
                new AliasList());
        try {
            expectedModel.depositInSavings(testSavings);
            expectedModel.addToHistory(testSavings);
        } catch (InsufficientFundsException | SavingsOutOfBoundException e) {
            System.err.println("This should never be executed");
        }
        assertCommandSuccess(saveCommand, model, expectedMessage, expectedModel);
    }
}
