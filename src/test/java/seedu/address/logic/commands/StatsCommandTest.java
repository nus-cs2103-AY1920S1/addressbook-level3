package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSpendings.ALICE;
import static seedu.address.testutil.TypicalSpendings.BENSON;
import static seedu.address.testutil.TypicalSpendings.CARL;
import static seedu.address.testutil.TypicalSpendings.DANIEL;
import static seedu.address.testutil.TypicalSpendings.ELLE;
import static seedu.address.testutil.TypicalSpendings.FIONA;
import static seedu.address.testutil.TypicalSpendings.GEORGE;
import static seedu.address.testutil.TypicalSpendings.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;


/**
 * Contains integration tests (interaction with the Model) and unit tests for StatsCommand.
 */
class StatsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_surplusBudgetRemaining_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        double totalCost = Double.parseDouble(ALICE.getCost().toString())
            + Double.parseDouble(BENSON.getCost().toString()) + Double.parseDouble(CARL.getCost().toString())
            + Double.parseDouble(DANIEL.getCost().toString()) + Double.parseDouble(ELLE.getCost().toString())
            + Double.parseDouble(FIONA.getCost().toString()) + Double.parseDouble(GEORGE.getCost().toString());

        double budget = model.getBudget().getValue();
        double budgetRemaining = budget - totalCost;

        String expectedMessage = StatsCommand.MESSAGE_SUCCESS
            + "\nTotal Cost: $" + String.format("%.2f", totalCost)
            + "\nBudget Set: $" + String.format("%.2f", budget)
            + "\nBudget Remaining: $" + String.format("%.2f", budgetRemaining)
            + "\nStatus: Surplus";
        System.out.println(expectedMessage);
        assertCommandSuccess(new StatsCommand(), model , expectedMessage, expectedModel);
    }

    @Test
    public void execute_deficitBudgetRemaining_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setBudget(new Budget(100));
        expectedModel.setBudget(new Budget(100));

        double totalCost = Double.parseDouble(ALICE.getCost().toString())
            + Double.parseDouble(BENSON.getCost().toString()) + Double.parseDouble(CARL.getCost().toString())
            + Double.parseDouble(DANIEL.getCost().toString()) + Double.parseDouble(ELLE.getCost().toString())
            + Double.parseDouble(FIONA.getCost().toString()) + Double.parseDouble(GEORGE.getCost().toString());

        double budget = 100;
        double budgetRemaining = budget - totalCost;

        String expectedMessage = StatsCommand.MESSAGE_SUCCESS
            + "\nTotal Cost: $" + String.format("%.2f", totalCost)
            + "\nBudget Set: $" + String.format("%.2f", budget)
            + "\nBudget Remaining: -$" + String.format("%.2f", -1 * budgetRemaining)
            + "\nStatus: Deficit";
        assertCommandSuccess(new StatsCommand(), model , expectedMessage, expectedModel);
    }

}
