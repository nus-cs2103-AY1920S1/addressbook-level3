package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBudgets.KOREA;
import static seedu.address.testutil.TypicalBudgets.getTypicalBudgetList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.budget.ReadOnlyBudgetList;
import seedu.address.model.budget.exceptions.DuplicateBudgetException;
import seedu.address.testutil.BudgetBuilder;

public class BudgetListTest {

    private final BudgetList budgetList = new BudgetList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), budgetList.getBudgetList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> budgetList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyBudgetList_replacesData() {
        BudgetList newData = getTypicalBudgetList();
        budgetList.resetData(newData);
        assertEquals(newData, budgetList);
    }

    @Test
    public void resetData_withDuplicateBudgets_throwsDuplicateBudgetException() {
        // Two budgets with the same identity fields
        Budget editedKorea = new BudgetBuilder(KOREA).build();
        List<Budget> newBudgets = Arrays.asList(KOREA, editedKorea);
        BudgetListStub newData = new BudgetListStub(newBudgets);

        assertThrows(DuplicateBudgetException.class, () -> budgetList.resetData(newData));
    }

    @Test
    public void hasBudget_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> budgetList.hasBudget(null));
    }

    @Test
    public void hasBudget_budgetNotInBudgetList_returnsFalse() {
        assertFalse(budgetList.hasBudget(KOREA));
    }

    @Test
    public void hasBudget_budgetInBudgetList_returnsTrue() {
        budgetList.addBudget(KOREA);
        assertTrue(budgetList.hasBudget(KOREA));
    }

    @Test
    public void getBudgetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> budgetList.getBudgetList().remove(0));
    }

    /**
     * A stub ReadOnlyBudgetList whose budgets list can violate interface constraints.
     */
    private static class BudgetListStub implements ReadOnlyBudgetList {

        private final ObservableList<Budget> budgets = FXCollections.observableArrayList();

        BudgetListStub(Collection<Budget> budgets) {
            this.budgets.setAll(budgets);
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }
    }
}
