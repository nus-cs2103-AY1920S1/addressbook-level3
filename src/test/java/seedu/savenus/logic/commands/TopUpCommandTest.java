package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TopUpCommand}.
 */
public class TopUpCommandTest {

    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
            new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());

    @Test
    public void execute_validTopUpAmount_success() {

        BigDecimal testTopUpAmount = new BigDecimal("100");
        TopUpCommand topUpCommand = new TopUpCommand(testTopUpAmount);

        ModelManager expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsAccount(), new AliasList());
        try {
            expectedModel.getWallet().setRemainingBudget(
                    new RemainingBudget(expectedModel.getWallet().getRemainingBudget().getRemainingBudgetAmount()
                            .add(testTopUpAmount).toString()));
        } catch (Exception e) {
            return;
        }

        String expectedMessage = String.format(TopUpCommand.MESSAGE_TOPUP_BUDGET_SUCCESS,
                expectedModel.getWallet().getRemainingBudget().toString());

        assertCommandSuccess(topUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTopUpAmount_failure() {

        BigDecimal testTopUpAmount = new BigDecimal("100000000");
        TopUpCommand topUpCommand = new TopUpCommand(testTopUpAmount);

        assertThrows(CommandException.class, () -> topUpCommand.execute(model));
    }


    @Test
    public void equals() {
        TopUpCommand topUpFirstCommand = new TopUpCommand(new BigDecimal(10.00));
        TopUpCommand topUpSecondCommand = new TopUpCommand(new BigDecimal(20.00));

        // same object -> returns true
        assertTrue(topUpFirstCommand.equals(topUpFirstCommand));

        // same values -> returns true
        TopUpCommand topUpFirstCommandCopy = new TopUpCommand(new BigDecimal(10.00));
        assertTrue(topUpFirstCommandCopy.equals(topUpFirstCommand));

        // different types -> returns false
        assertFalse(topUpFirstCommand.equals(1));

        // null -> returns false
        assertFalse(topUpFirstCommand.equals(null));

        // different food -> returns false
        assertFalse(topUpFirstCommand.equals(topUpSecondCommand));
    }
}
