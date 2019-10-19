package seedu.moneygowhere.logic.commands;

import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.testutil.TypicalSpendings.*;

import javax.crypto.EncryptedPrivateKeyInfo;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.util.StringUtil;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.budget.Budget;


/**
 * Contains integration tests (interaction with the Model) and unit tests for StatsCommand.
 */
class StatsCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    //Testing logic of calculating totalCost, budget and budgetRemaining
    //Testing whether list has been filtered correctly (no date parameters specified)
    @Test
    public void execute_surplusBudgetRemaining_success() {
        Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

        double totalCost = Double.parseDouble(APPLE.getCost().toString())
            + Double.parseDouble(BANANA.getCost().toString()) + Double.parseDouble(CATFOOD.getCost().toString())
            + Double.parseDouble(DESSERT.getCost().toString()) + Double.parseDouble(ENCYCLOPEDIA.getCost().toString())
            + Double.parseDouble(FLIGHTTICKET.getCost().toString()) + Double.parseDouble(GLASSES.getCost().toString());

        double budget = model.getBudget().getValue();
        double budgetRemaining = budget - totalCost;

        String expectedMessage = StatsCommand.MESSAGE_SUCCESS
            + "\nTotal Cost: $" + String.format("%.2f", totalCost)
            + "\nBudget: $" + String.format("%.2f", budget)
            + "\nBudget Remaining: $" + String.format("%.2f", budgetRemaining)
            + "\nStatus: Safe"
            + "\n\nSpending by Tags:"
            + "\n1. supper: $15.00 (1.94%)"
            + "\n2. fruit: $2.00 (0.26%)";

        assertCommandSuccess(new StatsCommand(), model , expectedMessage, expectedModel);
    }

    //Testing logic of calculating totalCost, budget and budgetRemaining
    //Testing whether list has been filtered correctly (no date parameters specified)
    @Test
    public void execute_deficitBudgetRemaining_success() {
        Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        model.setBudget(new Budget(100));
        expectedModel.setBudget(new Budget(100));

        double totalCost = Double.parseDouble(APPLE.getCost().toString())
            + Double.parseDouble(BANANA.getCost().toString()) + Double.parseDouble(CATFOOD.getCost().toString())
            + Double.parseDouble(DESSERT.getCost().toString()) + Double.parseDouble(ENCYCLOPEDIA.getCost().toString())
            + Double.parseDouble(FLIGHTTICKET.getCost().toString()) + Double.parseDouble(GLASSES.getCost().toString());

        double budget = 100;
        double budgetRemaining = budget - totalCost;

        String expectedMessage = StatsCommand.MESSAGE_SUCCESS
            + "\nTotal Cost: $" + String.format("%.2f", totalCost)
            + "\nBudget: $" + String.format("%.2f", budget)
            + "\nBudget Remaining: -$" + String.format("%.2f", -1 * budgetRemaining)
            + "\nStatus: Deficit"
            + "\n\nSpending by Tags:"
            + "\n1. supper: $15.00 (1.94%)"
            + "\n2. fruit: $2.00 (0.26%)";

        assertCommandSuccess(new StatsCommand(), model , expectedMessage, expectedModel);
    }

    //Testing logic of calculating totalCost, budget and budgetRemaining
    //Testing whether list has been filtered correctly (2 valid date parameters specified)
    @Test
    public void execute_validDateRange_success() {
        Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        model.setBudget(new Budget(100));
        expectedModel.setBudget(new Budget(100));

        double totalCost = Double.parseDouble(APPLE.getCost().toString())
            + Double.parseDouble(BANANA.getCost().toString());

        double budget = 100;
        double budgetRemaining = budget - totalCost;

        String expectedMessage = StatsCommand.MESSAGE_SUCCESS
            + "\nTotal Cost: $" + String.format("%.2f", totalCost)
            + "\nBudget: $" + String.format("%.2f", budget)
            + "\nBudget Remaining: $" + String.format("%.2f", budgetRemaining)
            + "\nStatus: Safe"
            + "\n\nSpending by Tags:"
            + "\n1. fruit: $2.00 (100.00%)";

        assertCommandSuccess(new StatsCommand(APPLE.getDate(), BANANA.getDate()),
            model , expectedMessage, expectedModel);
    }

    @Test
    public void execute_spendingWithNoTags_success() {
        Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        model.setBudget(new Budget(100));
        expectedModel.setBudget(new Budget(100));

        double totalCost = Double.parseDouble(ENCYCLOPEDIA.getCost().toString())
            + Double.parseDouble(FLIGHTTICKET.getCost().toString()) + Double.parseDouble(GLASSES.getCost().toString());

        double budget = 100;
        double budgetRemaining = budget - totalCost;

        String expectedMessage = StatsCommand.MESSAGE_SUCCESS
            + "\nTotal Cost: $" + String.format("%.2f", totalCost)
            + "\nBudget: $" + String.format("%.2f", budget)
            + "\nBudget Remaining: -$" + String.format("%.2f", -1 * budgetRemaining)
            + "\nStatus: Deficit"
            + "\n\nSpending by Tags:"
            + "\nNone";

        assertCommandSuccess(new StatsCommand(ENCYCLOPEDIA.getDate(), GLASSES.getDate()), model ,
            expectedMessage, expectedModel);
    }

}
