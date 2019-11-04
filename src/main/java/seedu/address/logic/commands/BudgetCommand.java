package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetGraph;
import seedu.address.model.claim.Claim;
import seedu.address.model.income.Income;

/**
 * Calculates projected budget for users.
 * Budget being total income minus total claim value.
 */

public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the projected budget, which takes in all income values and subtracts the values\n"
            + "of all the approved claims."
            + "It also generates a graph showing statistics for the current month";

    @Override
    public CommandResult execute(Model model) {
        List<Claim> claimList = model.getFilteredClaimList();
        List<Income> incomeList = model.getFilteredIncomeList();

        String message;

        Budget budget = new Budget(claimList, incomeList);
        BudgetGraph budgetGraph = new BudgetGraph(claimList, incomeList);
        budgetGraph.displayBudgetGraph();
        budget.calculateBudget();

        message = "Projected income: "
                + budget.getTotalIncome()
                + "\nProjected claim value: "
                + budget.getTotalExpenses()
                + "\nProjected Budget: "
                + budget.getBudgetAmount();

        if (budget.isOverBudget()) {
            message += "\nWarning, you will be over budget!";
        }


        return new CommandResult(message, false, false, false, false, false);
    }
}
