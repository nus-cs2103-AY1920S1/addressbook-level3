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
import seedu.savenus.model.savings.exceptions.InsufficientSavingsException;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.util.Money;
import seedu.savenus.model.util.TimeStamp;

/**
 * Contains integration tests (interaction with the model) and unit tests for
 * {@code WithdrawCommand}
 */
public class WithdrawCommandTest {

    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
            new PurchaseHistory(), getTypicalWallet(), new CustomSorter(), new SavingsHistory(),
            new SavingsAccount(new CurrentSavings(new Money("200.00"))),
            new AliasList());

    @Test
    public void execute_validWithdrawalsAmount_success() {
        String time = TimeStamp.generateCurrentTimeStamp();
        WithdrawCommand withdrawCommand = new WithdrawCommand("10.00", time);
        Savings testWithdraw = new Savings("10.00", time, true);
        String expectedMessage = String.format(WithdrawCommand.MESSAGE_WITHDRAW_SUCCESS, testWithdraw.toString());
        ModelManager expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), model.getWallet(), new CustomSorter(),
                new SavingsHistory(),
                new SavingsAccount(new CurrentSavings(new Money("200.00"))),
                new AliasList());
        try {
            expectedModel.withdrawFromSavings(testWithdraw);
            expectedModel.addToHistory(testWithdraw);
        } catch (InsufficientSavingsException e) {
            System.err.println("This should never be executed");
        }
        assertCommandSuccess(withdrawCommand, model, expectedMessage, expectedModel);
    }
}
