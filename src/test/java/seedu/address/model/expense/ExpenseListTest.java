package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ModelTestUtil.VALID_AMOUNT_EXPENSE_2;
import static seedu.address.model.ModelTestUtil.VALID_EXPENSE_1;
import static seedu.address.model.ModelTestUtil.VALID_EXPENSE_2;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.itinerary.Budget;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseListTest {



    @Test
    void contains_nullExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.contains(null));
    }

    @Test
    void contains_expenseNotInList_returnsFalse() {
        ExpenseList expenseList = new ExpenseList();
        assertFalse(expenseList.contains(VALID_EXPENSE_1));
    }

    @Test
    void contains_expenseInList_returnsTrue() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            assertTrue(expenseList.contains(VALID_EXPENSE_1));
        });
    }

    @Test
    void contains_expenseWithSameIdentityFieldsInList_returnsTrue() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            Expense editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1).setBudget(new Budget(10))
                    .build();
            assertTrue(expenseList.contains(editedExpenseA));
        });
    }

    @Test
    void add_nullExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.add(null));
    }

    @Test
    void add_duplicateExpense_throwsDuplicateExpenseException() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            assertThrows(DuplicateExpenseException.class, () -> expenseList.add(VALID_EXPENSE_1));
        });
    }

    @Test
    void setExpense_nullTargetExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.set(null, VALID_EXPENSE_1));
    }

    @Test
    void setExpense_nullEditedExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.set(VALID_EXPENSE_1, null));
    }

    @Test
    void setExpense_targetExpenseNotInList_throwsExpenseNotFoundException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(ExpenseNotFoundException.class, () -> expenseList.set(VALID_EXPENSE_1, VALID_EXPENSE_1));
    }

    @Test
    void setExpense_editedExpenseIsSameExpense_success() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            expenseList.set(VALID_EXPENSE_1, VALID_EXPENSE_1);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(VALID_EXPENSE_1);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    void setExpense_editedExpenseHasSameIdentity_success() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            Expense editedExpense = ExpenseBuilder.of(VALID_EXPENSE_1)
                    .setBudget(new Budget(VALID_AMOUNT_EXPENSE_2))
                    .build();
            expenseList.set(VALID_EXPENSE_1, editedExpense);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(editedExpense);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    void setExpense_editedExpenseHasDifferentIdentity_success() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            expenseList.set(VALID_EXPENSE_1, VALID_EXPENSE_2);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(VALID_EXPENSE_2);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    public void setExpense_editedExpenseHasNonUniqueIdentity_throwsDuplicateExpenseException() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            expenseList.add(VALID_EXPENSE_2);
            assertThrows(DuplicateExpenseException.class, () -> expenseList.set(VALID_EXPENSE_1, VALID_EXPENSE_2));
        });
    }

    @Test
    public void remove_nullExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.remove((Expense) null));
    }

    @Test
    public void remove_expenseDoesNotExist_throwsExpenseNotFoundException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(ExpenseNotFoundException.class, () -> expenseList.remove(VALID_EXPENSE_1));
    }

    @Test
    public void remove_existingExpense_removesExpense() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            expenseList.removeByUser(VALID_EXPENSE_1);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    public void setExpenses_uniqueExpenseList_replacesOwnListWithProvidedUniqueExpenseList() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            List<Expense> expectedUniqueExpenseList = new ArrayList<Expense>();
            expectedUniqueExpenseList.add(VALID_EXPENSE_2);
            expenseList.set(expectedUniqueExpenseList);
            assertEquals(expectedUniqueExpenseList, expenseList.asUnmodifiableObservableList());
        });
    }
    //-------------------------------------------------------------------

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.set((List<Expense>) null));
    }

    @Test
    public void setExpenses_list_replacesOwnListWithProvidedList() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(VALID_EXPENSE_1);
            List<Expense> expenses = Collections.singletonList(VALID_EXPENSE_2);
            expenseList.set(expenses);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(VALID_EXPENSE_2);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    public void setExpenses_listWithDuplicateExpenses_throwsDuplicateExpenseException() {
        ExpenseList expenseList = new ExpenseList();
        List<Expense> listWithDuplicateExpenses = Arrays.asList(VALID_EXPENSE_1, VALID_EXPENSE_1);
        assertThrows(DuplicateExpenseException.class, () -> expenseList.set(listWithDuplicateExpenses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(UnsupportedOperationException.class, () ->
                expenseList.asUnmodifiableObservableList().remove(0));
    }

}
