package seedu.address.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSecWithNoApprovedClaims;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSecWithApprovedClaims;

import org.junit.jupiter.api.Test;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.income.Income;


public class BudgetTest {

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
    public void execute_noData_allMethodsSuccess() {
        assertEquals(new Budget(null, null).getTotalExpenses(), "$0.00");
        assertEquals(new Budget(null, null).getTotalIncome(), "$0.00");
        assertEquals(new Budget(null, null).getBudgetAmount(), "$0.00");
        assertFalse(new Budget(null, null).isOverBudget());
    }

    @Test
    public void execute_noClaims_allMethodsSuccess() {
        Budget budget = new Budget(claimList, incomeList);
        budget.calculateBudget();

        assertEquals(budget.getTotalExpenses(), "$0.00");
        assertEquals(budget.getTotalIncome(), "$10,100.10");
        assertEquals(budget.getBudgetAmount(), "$10,100.10");
        assertFalse(budget.isOverBudget());
    }

    @Test
    public void execute_nonApprovedClaims_allMethodsSuccess() {
        Budget budget = new Budget(claimList2, incomeList2);
        budget.calculateBudget();

        assertEquals(budget.getTotalExpenses(), "$0.00");
        assertEquals(budget.getTotalIncome(), "$10,100.10");
        assertEquals(budget.getBudgetAmount(), "$10,100.10");
        assertFalse(budget.isOverBudget());
    }

    @Test
    public void execute_ApprovedClaims_allMethodsSuccess() {
        Budget budget = new Budget(claimList3, incomeList3);
        budget.calculateBudget();

        assertEquals(budget.getTotalExpenses(), "$10,911.29");
        assertEquals(budget.getTotalIncome(), "$10,100.10");
        assertEquals(budget.getBudgetAmount(), "-$811.19");
        assertTrue(budget.isOverBudget());
    }

}
