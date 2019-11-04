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

public class BudgetCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());
    private Model model2 = new ModelManager(getTypicalFinSecWithNoApprovedClaims(), new UserPrefs());
    private Model model3 = new ModelManager(getTypicalFinSecWithApprovedClaims(), new UserPrefs());
    private List<Claim> claimList = model.getFilteredClaimList();
    private List<Income> incomeList = model.getFilteredIncomeList();
    private List<Claim> claimList2 = model2.getFilteredClaimList();
    private List<Income> incomeList2 = model2.getFilteredIncomeList();
    private List<Claim> claimList3 = model3.getFilteredClaimList();
    private List<Income> incomeList3 = model3.getFilteredIncomeList();

    @Test
    public void execute_noData_budgetSuccess() {
        String noBudgetData = "Projected income: $0.00\n"
                + "Projected claim value: $0.00\n"
                + "Projected Budget: $0.00";
        assertEquals(noBudgetData, new BudgetCommand().execute(new ModelManager()).getFeedbackToUser());
    }

    @Test
    public void execute_noClaims_budgetSuccess() {
        Budget budget = new Budget(claimList, incomeList);
        budget.calculateBudget();
        String noClaimData = "Projected income: $10,100.10\n"
                + "Projected claim value: $0.00\n"
                + "Projected Budget: $10,100.10";
        assertEquals(noClaimData, new BudgetCommand().execute(model).getFeedbackToUser());
    }

    @Test
    public void execute_nonApprovedClaims_budgetSuccess() {
        Budget budget = new Budget(claimList2, incomeList2);
        budget.calculateBudget();
        String noApprovedClaimData = "Projected income: $10,100.10\n"
                + "Projected claim value: $0.00\n"
                + "Projected Budget: $10,100.10";
        assertEquals(noApprovedClaimData,  new BudgetCommand().execute(model2).getFeedbackToUser());
    }

    @Test
    public void execute_approvedClaims_budgetSuccess() {
        Budget budget = new Budget(claimList3, incomeList3);
        budget.calculateBudget();
        String approvedClaimData = "Projected income: $10,100.10\n"
                + "Projected claim value: $10,911.29\n"
                + "Projected Budget: -$811.19\n"
                + "Warning, you will be over budget!";
        assertEquals(approvedClaimData, new BudgetCommand().execute(model3).getFeedbackToUser());
    }

}
