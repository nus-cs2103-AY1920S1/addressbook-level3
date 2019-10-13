package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALCOHOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RUM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.FOOD;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseListTest {

    private final ExpenseList expenseList = new ExpenseList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), expenseList.getExpenseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExpenseList_replacesData() {
        ExpenseList newData = getTypicalExpenseList();
        expenseList.resetData(newData);
        assertEquals(newData, expenseList);
    }

    @Test
    public void resetData_withDuplicateExpenses_throwsDuplicateExpenseException() {
        // Two expenses with the same identity fields
        Expense editedFood = new ExpenseBuilder(FOOD).withAmount(VALID_AMOUNT_RUM).withTags(VALID_TAG_ALCOHOL)
                .build();
        List<Expense> newExpenses = Arrays.asList(FOOD, editedFood);
        ExpenseListStub newData = new ExpenseListStub(newExpenses);

        assertThrows(DuplicateExpenseException.class, () -> expenseList.resetData(newData));
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.hasExpense(null));
    }

    @Test
    public void hasExpense_expenseNotInExpenseList_returnsFalse() {
        assertFalse(expenseList.hasExpense(FOOD));
    }

    @Test
    public void hasExpense_expenseInExpenseList_returnsTrue() {
        expenseList.addExpense(FOOD);
        assertTrue(expenseList.hasExpense(FOOD));
    }

    @Test
    public void hasExpense_expenseWithSameIdentityFieldsInExpenseList_returnsTrue() {
        expenseList.addExpense(FOOD);
        Expense editedFood = new ExpenseBuilder(FOOD).withAmount(VALID_AMOUNT_RUM).withTags(VALID_TAG_ALCOHOL)
                .build();
        assertTrue(expenseList.hasExpense(editedFood));
    }

    @Test
    public void getExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> expenseList.getExpenseList().remove(0));
    }

    /**
     * A stub ReadOnlyExpenseList whose expenses list can violate interface constraints.
     */
    private static class ExpenseListStub implements ReadOnlyExpenseList {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

        ExpenseListStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
        }
    }

}
