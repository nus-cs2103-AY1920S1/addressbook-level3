package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.CHICKEN_RICE;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.RecommendationSystem;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.savings.SavingsHistory;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;

//@@author jon-chua
/**
 * Contains integration tests and unit tests for RecommendCommand.
 */
public class RecommendCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(),
                new AliasList());
        expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(),
                new AliasList());

        RecommendationSystem.getInstance().updateDaysToExpire(0);
        RecommendationSystem.getInstance().updateBudget(BigDecimal.ZERO);
    }

    @Test
    public void execute_recommendCommand() {
        assertCommandSuccess(new RecommendCommand(), model,
                String.format(RecommendCommand.MESSAGE_SUCCESS + RecommendCommand.BUDGET_DAYS_NOT_SET,
                        RecommendationSystem.getInstance().getDailyBudget(),
                        RecommendationSystem.getInstance().getBudget(),
                        RecommendationSystem.getInstance().getDaysToExpire()),
                expectedModel);
        assertTrue(model.getRecommendationSystem().isInUse());
    }

    @Test
    public void recommendCommand_notInUse_afterList() {
        assertCommandSuccess(new RecommendCommand(), model,
                String.format(RecommendCommand.MESSAGE_SUCCESS + RecommendCommand.BUDGET_DAYS_NOT_SET,
                        RecommendationSystem.getInstance().getDailyBudget(),
                        RecommendationSystem.getInstance().getBudget(),
                        RecommendationSystem.getInstance().getDaysToExpire()),
                expectedModel);
        assertTrue(model.getRecommendationSystem().isInUse());

        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
        assertFalse(model.getRecommendationSystem().isInUse());
    }

    @Test
    public void recommendCommand_notInUse_afterAdd() throws CommandException {
        assertCommandSuccess(new RecommendCommand(), model,
                String.format(RecommendCommand.MESSAGE_SUCCESS + RecommendCommand.BUDGET_DAYS_NOT_SET,
                        RecommendationSystem.getInstance().getDailyBudget(),
                        RecommendationSystem.getInstance().getBudget(),
                        RecommendationSystem.getInstance().getDaysToExpire()),
                expectedModel);
        assertTrue(model.getRecommendationSystem().isInUse());

        CommandResult commandResult = new AddCommand(CHICKEN_RICE).execute(model);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, CHICKEN_RICE), commandResult.getFeedbackToUser());

        assertFalse(model.getRecommendationSystem().isInUse());
    }

    @Test
    public void recommendCommand_budgetSet_doesNotShowMessage() throws CommandException {
        RecommendationSystem.getInstance().updateDaysToExpire(10);
        RecommendationSystem.getInstance().updateBudget(BigDecimal.TEN);

        assertCommandSuccess(new RecommendCommand(), model,
                String.format(RecommendCommand.MESSAGE_SUCCESS + RecommendCommand.BUDGET_SET,
                        RecommendationSystem.getInstance().getDailyBudget(),
                        RecommendationSystem.getInstance().getBudget(),
                        RecommendationSystem.getInstance().getDaysToExpire()),
                expectedModel);
        assertTrue(model.getRecommendationSystem().isInUse());

        CommandResult commandResult = new AddCommand(CHICKEN_RICE).execute(model);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, CHICKEN_RICE), commandResult.getFeedbackToUser());

        assertFalse(model.getRecommendationSystem().isInUse());
    }

    @Test
    public void equals() {
        final RecommendCommand standardCommand = new RecommendCommand();

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);
    }

}
