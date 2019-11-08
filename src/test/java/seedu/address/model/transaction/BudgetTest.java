package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.category.Category;
import seedu.address.model.util.Date;


public class BudgetTest {
    private static final Amount HUNDRED = new Amount(100);

    private static final String VALID_DATE = "31122025";
    private static final Set<Category> CATEGORIES = new HashSet<>();
    private static final String VALID_CATEGORIES = "food";

    private static final Budget BUDGET_ONE = new Budget();
    private static final Budget BUDGET_TWO = new Budget(HUNDRED, new Date(VALID_DATE));
    private static final Budget BUDGET_THREE = new Budget(HUNDRED, new Date(VALID_DATE), CATEGORIES);


    @Test
    public void budgetConstructor_overBoundary_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Budget(new Amount(Amount.UNSIGNED_INT_LIMIT + 1),
                new Date(VALID_DATE)));
        assertThrows(IllegalArgumentException.class, () -> new Budget(new Amount(Amount.UNSIGNED_INT_LIMIT + 1),
                new Date(VALID_DATE), CATEGORIES));
    }

    @Test
    public void getBudget_success() {
        assertEquals(HUNDRED, new Budget(HUNDRED, new Date(VALID_DATE)).getBudget());
        assertEquals(new Amount(10000), new Budget(new Amount(10000), new Date(VALID_DATE)).getBudget());
    }

    @Test
    public void getBudget_fail() {
        assertNotEquals(HUNDRED, new Budget(new Amount(10000), new Date(VALID_DATE)).getBudget());
    }

    @Test
    public void getInitialBudget_success() {
        assertEquals(HUNDRED, new Budget(HUNDRED, new Date(VALID_DATE)).getInitialBudget());
        assertEquals(new Amount(10000), new Budget(new Amount(10000), new Date(VALID_DATE)).getInitialBudget());
        assertEquals(HUNDRED, new Budget(HUNDRED, HUNDRED, new Date(VALID_DATE), CATEGORIES).getInitialBudget());
    }

    @Test
    public void getInitialBudget_fail() {
        assertNotEquals(HUNDRED, new Budget(new Amount(10000), new Date(VALID_DATE)).getInitialBudget());
    }


    @Test
    public void getDeadline_success() {
        assertEquals(new Date(VALID_DATE), new Budget(HUNDRED, new Date(VALID_DATE)).getDeadline());
        assertEquals(new Date("31122020"), new Budget(new Amount(10000), new Date("31122020")).getDeadline());
    }

    @Test
    public void getDeadline_fail() {
        assertNotEquals(new Date(VALID_DATE), new Budget(new Amount(100), new Date("31122020")).getDeadline());
    }

    @Test
    public void getCategories_success() {
        CATEGORIES.add(new Category(VALID_CATEGORIES));
        assertEquals(CATEGORIES, new Budget(HUNDRED, new Date(VALID_DATE), CATEGORIES).getCategories());
    }

    @Test
    public void getCategories_fail() {
        CATEGORIES.add(new Category(VALID_CATEGORIES));
        assertNotEquals(new HashSet<Category>(), new Budget(HUNDRED, new Date(VALID_DATE), CATEGORIES).getCategories());
    }

    @Test
    public void isValid_true() {
        assertTrue(new Budget(HUNDRED, new Date(VALID_DATE)).isValid());
        assertTrue(new Budget(HUNDRED, new Date(VALID_DATE), CATEGORIES).isValid());
    }

    @Test
    public void isValid_false() {
        Budget budget = new Budget();
        assertFalse(budget.isValid());
    }

    @Test
    public void isSameBudget_true() {
        assertTrue(BUDGET_ONE.isSameBudget(BUDGET_ONE));
        assertTrue(BUDGET_TWO.isSameBudget(BUDGET_TWO));
        assertTrue(BUDGET_THREE.isSameBudget(BUDGET_THREE));
    }

    @Test
    public void isSameBudget_false() {
        assertFalse(BUDGET_ONE.isSameBudget(null));
        assertFalse(BUDGET_ONE.isSameBudget(BUDGET_TWO));
        assertFalse(BUDGET_TWO.isSameBudget(BUDGET_THREE));
    }

    @Test
    public void displayBudget_success() {
        assertEquals("$1.00 out of $1.00 remaining", BUDGET_TWO.displayBudget());
    }

    @Test
    public void displayBudget_fail() {
        assertNotEquals("$1 out of $1 remaining", BUDGET_TWO.displayBudget());
        assertThrows(NullPointerException.class, () -> BUDGET_ONE.displayBudget());
    }

    @Test
    public void displayPercentage_success() {
        assertEquals("100.00% remaining", BUDGET_TWO.displayPercentage());
    }

    @Test
    public void displayPercentage_fail() {
        assertNotEquals("100% remaining", BUDGET_TWO.displayPercentage());
        assertThrows(NullPointerException.class, () -> BUDGET_ONE.displayPercentage());
    }

}
