package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseListTest {

    public static final Expense EXPENSE_A = ExpenseBuilder.newInstance().setName(new Name("Expense A"))
            .setBudget(new Budget(123))
            .setDayNumber(new DayNumber("1"))
            .setType("misc")
            .build();
    public static final Expense EXPENSE_B = ExpenseBuilder.newInstance().setName(new Name("Expense B"))
            .setBudget(new Budget(123.12))
            .setDayNumber(new DayNumber("2"))
            .setType("planned")
            .build();

    @Test
    void contains_nullExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.contains(null));
    }

    @Test
    void contains_expenseNotInList_returnsFalse() {
        ExpenseList expenseList = new ExpenseList();
        assertFalse(expenseList.contains(EXPENSE_A));
    }

    @Test
    void contains_expenseInList_returnsTrue() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            assertTrue(expenseList.contains(EXPENSE_A));
        });
    }

    @Test
    void contains_expenseWithSameIdentityFieldsInList_returnsTrue() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            Expense editedExpenseA = ExpenseBuilder.of(EXPENSE_A).setBudget(new Budget(10))
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
            expenseList.add(EXPENSE_A);
            assertThrows(DuplicateExpenseException.class, () -> expenseList.add(EXPENSE_A));
        });
    }

    @Test
    void setExpense_nullTargetExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.set(null, EXPENSE_A));
    }

    @Test
    void setExpense_nullEditedExpense_throwsNullPointerException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(NullPointerException.class, () -> expenseList.set(EXPENSE_A, null));
    }

    @Test
    void setExpense_targetExpenseNotInList_throwsExpenseNotFoundException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(ExpenseNotFoundException.class, () -> expenseList.set(EXPENSE_A, EXPENSE_A));
    }

    @Test
    void setExpense_editedExpenseIsSameExpense_success() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            expenseList.set(EXPENSE_A, EXPENSE_A);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(EXPENSE_A);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    void setExpense_editedExpenseHasSameIdentity_success() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            Expense editedExpense = ExpenseBuilder.of(EXPENSE_A)
                    .setBudget(new Budget("100"))
                    .build();
            expenseList.set(EXPENSE_A, editedExpense);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(editedExpense);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    void setExpense_editedExpenseHasDifferentIdentity_success() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            expenseList.set(EXPENSE_A, EXPENSE_B);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(EXPENSE_B);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    public void setExpense_editedExpenseHasNonUniqueIdentity_throwsDuplicateExpenseException() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            expenseList.add(EXPENSE_B);
            assertThrows(DuplicateExpenseException.class, () -> expenseList.set(EXPENSE_A, EXPENSE_B));
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
        assertThrows(ExpenseNotFoundException.class, () -> expenseList.remove(EXPENSE_A));
    }

    @Test
    public void remove_existingExpense_removesExpense() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            expenseList.removeByUser(EXPENSE_A);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    /*
    //note list references in these two tests were originally of type ExpenseList
    @Test
    public void setExpenses_nullUniqueExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.set(null));
    }
    */

    @Test
    public void setExpenses_uniqueExpenseList_replacesOwnListWithProvidedUniqueExpenseList() {
        ExpenseList expenseList = new ExpenseList();
        assertDoesNotThrow(() -> {
            expenseList.add(EXPENSE_A);
            List<Expense> expectedUniqueExpenseList = new ArrayList<Expense>();
            expectedUniqueExpenseList.add(EXPENSE_B);
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
            expenseList.add(EXPENSE_A);
            List<Expense> expenses = Collections.singletonList(EXPENSE_B);
            expenseList.set(expenses);
            ExpenseList expectedUniqueExpenseList = new ExpenseList();
            expectedUniqueExpenseList.add(EXPENSE_B);
            assertEquals(expectedUniqueExpenseList, expenseList);
        });
    }

    @Test
    public void setExpenses_listWithDuplicateExpenses_throwsDuplicateExpenseException() {
        ExpenseList expenseList = new ExpenseList();
        List<Expense> listWithDuplicateExpenses = Arrays.asList(EXPENSE_A, EXPENSE_A);
        assertThrows(DuplicateExpenseException.class, () -> expenseList.set(listWithDuplicateExpenses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        ExpenseList expenseList = new ExpenseList();
        assertThrows(UnsupportedOperationException.class, () ->
                expenseList.asUnmodifiableObservableList().remove(0));
    }

}
