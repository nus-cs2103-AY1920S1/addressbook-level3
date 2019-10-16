package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.claim.Claim;
import seedu.address.model.income.Income;

/**
 * Calculates current budget for users.
 * Budget being total income minus total claim value.
 */

public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    @Override
    public CommandResult execute(Model model) {
        List<Claim> claimList = model.getFilteredClaimList();
        List<Income> incomeList = model.getFilteredIncomeList();

        String message;

        Budget budget = new Budget(claimList, incomeList);
        budget.calculateBudget();

        message = "Total income: "
                + budget.getTotalIncome()
                + "\nTotal claim value: "
                + budget.getTotalExpenses()
                + "\nBudget: "
                + budget.getBudgetAmount();

        if (budget.isOverBudget()) {
            message += "\nWarning, you are over budget!";
        }

        return new CommandResult(message, false, false, false);
    }
}
