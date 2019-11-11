package seedu.moolah.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_CHICKEN;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.ANNIVERSARY;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.exceptions.DuplicateExpenseException;
import seedu.moolah.testutil.ExpenseBuilder;

public class MooLahTest {

    private final MooLah mooLah = new MooLah();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mooLah.getExpenseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mooLah.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMooLah_replacesData() {
        MooLah newData = getTypicalMooLah();
        mooLah.resetData(newData);
        assertEquals(newData, mooLah);
    }

    @Test
    public void resetData_withDuplicateExpenses_throwsDuplicateExpenseException() {
        // Two expenses with the same identity fields
        Expense editedAlice = new ExpenseBuilder(ANNIVERSARY).withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();
        List<Expense> newExpenses = Arrays.asList(ANNIVERSARY, editedAlice);
        MooLahStub newData = new MooLahStub(newExpenses);

        assertThrows(DuplicateExpenseException.class, () -> mooLah.resetData(newData));
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mooLah.hasExpense(null));
    }

    @Test
    public void hasExpense_expenseNotInMooLah_returnsFalse() {
        assertFalse(mooLah.hasExpense(ANNIVERSARY));
    }

    @Test
    public void hasExpense_expenseInMooLah_returnsTrue() {
        mooLah.addExpense(ANNIVERSARY);
        assertTrue(mooLah.hasExpense(ANNIVERSARY));
    }

    @Test
    public void getExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> mooLah.getExpenseList().remove(0));
    }

    /**
     * A stub ReadOnlyMooLah whose expenses list can violate interface constraints.
     */
    private static class MooLahStub implements ReadOnlyMooLah {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
        private final ObservableList<Budget> budgets = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final String primaryBudgetName = "Default Budget";

        MooLahStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ReadOnlyMooLah copy() {
            return new MooLahStub(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }

        @Override
        public String getPrimaryBudgetName() {
            return primaryBudgetName;
        }
    }

}
