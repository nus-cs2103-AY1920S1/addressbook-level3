package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import thrift.testutil.TypicalTransactions;

public class BudgetListTest {

    private final BudgetList budgetList = new BudgetList();

    @Test
    public void addValidBudget_success() {
        Calendar now = Calendar.getInstance();
        assertDoesNotThrow(() -> budgetList.setBudget(new Budget(now, new BudgetValue("123"))));
    }

    @Test
    public void addNullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> budgetList.setBudget(null));
    }

    @Test
    public void updateBudget_success() {
        Calendar now = Calendar.getInstance();
        assertDoesNotThrow(() -> budgetList.setBudget(new Budget(now, new BudgetValue("321"))));
    }

    @Test
    public void setBudget_budgetNotInThrift_returnsTrue() {
        budgetList.setBudget(TypicalTransactions.OCT_BUDGET);
        Calendar now = Calendar.getInstance();
        assertTrue(budgetList.getBudgetForMonthYear(now).isPresent());
    }
}
