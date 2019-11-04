package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSecWithApprovedClaims;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSecWithNoApprovedClaims;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;
import seedu.address.model.claim.Claim;
import seedu.address.model.income.Income;

public class BudgetCommandTest extends BudgetCommand {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());
    private Model model2 = new ModelManager(getTypicalFinSecWithNoApprovedClaims(), new UserPrefs());
    private Model model3 = new ModelManager(getTypicalFinSecWithApprovedClaims(), new UserPrefs());

    @Test
    public void execute_noData_budgetSuccess() {
        CommandResult commandResult = new BudgetCommandTest().execute(new ModelManager());
        String noBudgetData = "Projected income: $0.00\n"
                + "Projected claim value: $0.00\n"
                + "Projected Budget: $0.00";
        assertEquals(noBudgetData, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_noClaims_budgetSuccess() {
        CommandResult commandResult = new BudgetCommandTest().execute(model);
        String noClaimData = "Projected income: $10,100.10\n"
                + "Projected claim value: $0.00\n"
                + "Projected Budget: $10,100.10";
        assertEquals(noClaimData, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonApprovedClaims_budgetSuccess() {
        CommandResult commandResult = new BudgetCommandTest().execute(model2);
        String noApprovedClaimData = "Projected income: $10,100.10\n"
                + "Projected claim value: $0.00\n"
                + "Projected Budget: $10,100.10";
        assertEquals(noApprovedClaimData, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_approvedClaims_budgetSuccess() {
        CommandResult commandResult = new BudgetCommandTest().execute(model3);
        String approvedClaimData = "Projected income: $10,100.10\n"
                + "Projected claim value: $10,911.29\n"
                + "Projected Budget: -$811.19\n"
                + "Warning, you will be over budget!";
        assertEquals(approvedClaimData, commandResult.getFeedbackToUser());
    }

    @Override
    public CommandResult execute(Model model) {
        List<Claim> claimList = model.getFilteredClaimList();
        List<Income> incomeList = model.getFilteredIncomeList();
        Budget budget = new Budget(claimList, incomeList);
        budget.calculateBudget();

        String message;
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
