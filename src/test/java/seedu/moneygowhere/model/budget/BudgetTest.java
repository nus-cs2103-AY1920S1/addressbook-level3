package seedu.moneygowhere.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.SpendingBuilder;

class BudgetTest {

    private Budget oldBudget;
    private LocalDate oldDate;
    private Budget newBudget;
    private LocalDate newDate;

    @BeforeEach
    public void setUp() {
        oldBudget = new Budget(100, 10, "01/2010");
        oldDate = LocalDate.of(2010, 01, 01);
        newBudget = new Budget(123, 12, "12/2020");
        newDate = LocalDate.of(2020, 12, 01);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudgetValue_throwsIllegalArgumentException() {
        String invalidBudget = "";
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        //invalid Budgets
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // spaces only
        assertFalse(Budget.isValidBudget("incorrect")); // strings need to be numbers
        assertFalse(Budget.isValidBudget("-1")); //  numeric strings need to be positive
        assertFalse(Budget.isValidBudget(-1)); //  cannot be negative

        //valid Budget
        assertTrue(Budget.isValidBudget("0"));
        assertTrue(Budget.isValidBudget("0.123"));
        assertTrue(Budget.isValidBudget("10000"));
        assertTrue(Budget.isValidBudget(0));
        assertTrue(Budget.isValidBudget(0.123));
        assertTrue(Budget.isValidBudget(10000));
    }

    @Test
    public void update_oldMonth_noUpdate() {
        Budget temp = new Budget(123, 12, "12/2020");
        temp.update(oldDate);
        assertEquals(temp, newBudget);
    }

    @Test
    public void update_sameMonth_noUpdate() {
        Budget temp = new Budget(123, 12, "12/2020");
        temp.update(newDate);
        assertEquals(temp, newBudget);
    }

    @Test
    public void update_differentMonth_update() {
        Budget cleanBudget = new Budget(100, 0, "12/2020");
        Budget temp = new Budget(100, 10, "01/2010");

        temp.update(newDate);
        assertEquals(temp, cleanBudget);
    }

    @Test
    public void inSameMonth_validInput_success() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        assertTrue(oldBudget.inSameMonth(temp));
        assertFalse(newBudget.inSameMonth(temp));
    }

    @Test
    public void addSpending_wrongDate_noAdd() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        Budget newBudgetTemp = new Budget(123, 12, "12/2020");
        newBudgetTemp.addSpending(temp);
        assertEquals(newBudget, newBudgetTemp);
    }

    @Test
    public void addSpending_validDate_success() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        Budget oldBudgetTemp = new Budget(100, 10, "01/2010");
        oldBudgetTemp.addSpending(temp);
        assertEquals(oldBudget.getSum() + 10, oldBudgetTemp.getSum());
    }

    @Test
    public void deleteSpending_wrongDate_noAdd() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        double originalValue = newBudget.getValue();
        double originalSum = newBudget.getSum();

        newBudget.deleteSpending(temp);
        assertEquals(originalValue, newBudget.getValue());
        assertEquals(originalSum, newBudget.getSum());
    }

    @Test
    public void deleteSpending_validDate_success() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        double originalSum = oldBudget.getSum();
        oldBudget.deleteSpending(temp);
        assertEquals(originalSum - 10, oldBudget.getSum());
    }
}
