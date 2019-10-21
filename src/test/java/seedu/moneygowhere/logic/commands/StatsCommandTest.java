package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.logic.commands.StatsCommand.SHOWING_STATS_MESSAGE;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;
import static seedu.moneygowhere.testutil.TypicalSpendings.CATFOOD;
import static seedu.moneygowhere.testutil.TypicalSpendings.DESSERT;
import static seedu.moneygowhere.testutil.TypicalSpendings.ENCYCLOPEDIA;
import static seedu.moneygowhere.testutil.TypicalSpendings.FLIGHTTICKET;
import static seedu.moneygowhere.testutil.TypicalSpendings.GLASSES;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model) and unit tests for StatsCommand.
 */
class StatsCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    //Testing logic of calculating totalCost, budget and budgetRemaining
    //Testing whether list has been filtered correctly (no date parameters specified)

    @Test
    public void execute_statsMessage_success() {
        Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(SHOWING_STATS_MESSAGE, false, true, false);
        assertCommandSuccess(new StatsCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_statsMessageValidDateRange_success() {
        Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(SHOWING_STATS_MESSAGE, false, true, false);
        assertCommandSuccess(new StatsCommand(APPLE.getDate(), GLASSES.getDate()), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void getStatsMessage_surplusBudgetRemaining_success() {

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

        assertEquals(new StatsCommand().getStatsMessage(model), expectedMessage);
    }

    //Testing logic of calculating totalCost, budget and budgetRemaining
    //Testing whether list has been filtered correctly (no date parameters specified)
    @Test
    public void getStatsMessage_deficitBudgetRemaining_success() {
        model.setBudget(new Budget(100));

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

        assertEquals(new StatsCommand().getStatsMessage(model), expectedMessage);
    }

    //Testing logic of calculating totalCost, budget and budgetRemaining
    //Testing whether list has been filtered correctly (2 valid date parameters specified)
    @Test
    public void getStatsMessage_validDateRange_success() {
        model.setBudget(new Budget(100));

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

        assertEquals(new StatsCommand(APPLE.getDate(), BANANA.getDate()).getStatsMessage(model), expectedMessage);
    }

    @Test
    public void getStatsMessage_spendingWithNoTags_success() {
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

        assertEquals(new StatsCommand(ENCYCLOPEDIA.getDate(),
            GLASSES.getDate()).getStatsMessage(model), expectedMessage);
    }

    @Test
    public void getStatsData_statsDataValidDateRange_success() {
        Map<Tag, Double> costPerTagList = new HashMap<>();
        costPerTagList.put(APPLE.getTags().iterator().next(), Double.parseDouble(APPLE.getCost().toString())
            + Double.parseDouble(BANANA.getCost().toString()));
        assertEquals(costPerTagList, new StatsCommand(APPLE.getDate(), BANANA.getDate()).getStatsData(model));
    }

}
