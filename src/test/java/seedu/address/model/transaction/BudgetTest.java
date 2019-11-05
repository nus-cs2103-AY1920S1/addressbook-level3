package seedu.address.model.transaction;

import org.junit.jupiter.api.Test;
import seedu.address.model.category.Category;
import seedu.address.model.util.Date;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

public class BudgetTest {
    private static final Amount ONE = new Amount(1);

    private static final String PAST_DATE = "01011990";
    private static final String VALID_DATE = "31122025";
    private static final Set<Category> CATEGORIES = new HashSet<>();

    @Test
    public void budgetConstructor_overBoundary_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Budget(new Amount(Amount.UNSIGNED_INT_LIMIT + 1),
                new Date(VALID_DATE)));
        assertThrows(IllegalArgumentException.class, () -> new Budget(new Amount(Amount.UNSIGNED_INT_LIMIT + 1),
                new Date(VALID_DATE), CATEGORIES));
    }

    @Test
    public void isValid_true() {
        assertTrue(new Budget(ONE, new Date(VALID_DATE)).isValid());
        assertTrue(new Budget(ONE, new Date(VALID_DATE), CATEGORIES).isValid());
    }

    @Test
    public void isValid_false() {
        Budget budget = new Budget();
        assertFalse(budget.isValid());
    }

    @Test
    public void isSameBudget_true() {
        Budget budget_one = new Budget();
        Budget budget_two = new Budget(ONE, new Date(VALID_DATE));
        Budget budget_three = new Budget(ONE, new Date(VALID_DATE), CATEGORIES);

        assertTrue(budget_one.isSameBudget(budget_one));
        assertTrue(budget_two.isSameBudget(budget_two));
        assertTrue(budget_three.isSameBudget(budget_three));
    }

    @Test
    public void isSameBudget_false() {
        Budget budget_one = new Budget();
        Budget budget_two = new Budget(ONE, new Date(VALID_DATE));
        Budget budget_three = new Budget(ONE, new Date(VALID_DATE), CATEGORIES);

        assertFalse(budget_one.isSameBudget(null));
        assertFalse(budget_one.isSameBudget(budget_two));
        assertFalse(budget_two.isSameBudget(budget_three));
    }

    @Test
    public void getBudget_success() {
        assertEquals(ONE, new Budget(ONE, new Date(VALID_DATE)).getBudget());
        assertEquals(new Amount(10000), new Budget(new Amount(10000), new Date(VALID_DATE)).getBudget());
    }

    @Test
    public void getBudget_fail() {
        assertNotEquals(ONE, new Budget(new Amount(100), new Date(VALID_DATE)).getBudget());
    }

    @Test
    public void getDeadline_success() {
        assertEquals(new Date(VALID_DATE), new Budget(ONE, new Date(VALID_DATE)).getDeadline());
        assertEquals(new Date("31122020"), new Budget(new Amount(10000), new Date("31122020")).getDeadline());
    }

    @Test
    public void getDeadline_fail() {
        assertNotEquals(new Date(VALID_DATE), new Budget(new Amount(100), new Date("31122020")).getDeadline());
    }
}
