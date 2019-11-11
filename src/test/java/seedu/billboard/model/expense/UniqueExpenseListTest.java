package seedu.billboard.model.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.TAXES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.expense.exceptions.DuplicateExpenseException;
import seedu.billboard.model.expense.exceptions.ExpenseNotFoundException;
import seedu.billboard.testutil.ExpenseBuilder;

public class UniqueExpenseListTest {

    private final ExpenseList expenseList = new ExpenseList();

    @Test
    public void contains_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.contains(null));
    }

    @Test
    public void contains_expenseNotInList_returnsFalse() {
        assertFalse(expenseList.contains(BILLS));
    }

    @Test
    public void contains_expenseInList_returnsTrue() {
        expenseList.add(BILLS);
        assertTrue(expenseList.contains(BILLS));
    }

    @Test
    public void add_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.add(null));
    }

    @Test
    public void add_duplicateExpense_throwsDuplicateExpenseException() {
        expenseList.add(BILLS);
        assertThrows(DuplicateExpenseException.class, () -> expenseList.add(BILLS));
    }

    @Test
    public void setExpense_nullTargetExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setExpense(null, BILLS));
    }

    @Test
    public void setExpense_nullEditedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setExpense(BILLS, null));
    }

    @Test
    public void setExpense_targetExpenseNotInList_throwsExpenseNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> expenseList.setExpense(BILLS, BILLS));
    }

    @Test
    public void setExpense_editedExpenseIsSameExpense_success() {
        expenseList.add(BILLS);
        expenseList.setExpense(BILLS, BILLS);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(BILLS);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpense_editedExpenseHasSameIdentity_success() {
        expenseList.add(BILLS);
        Expense editedAlice = new ExpenseBuilder(BILLS).withAmount(VALID_AMOUNT_TAXES).withTags(VALID_TAG_DINNER)
                .build();
        expenseList.setExpense(BILLS, editedAlice);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(editedAlice);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpense_editedExpenseHasDifferentIdentity_success() {
        expenseList.add(BILLS);
        expenseList.setExpense(BILLS, TAXES);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(TAXES);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpense_editedExpenseHasNonUniqueIdentity_throwsDuplicateExpenseException() {
        expenseList.add(BILLS);
        expenseList.add(TAXES);
        assertThrows(DuplicateExpenseException.class, () -> expenseList.setExpense(BILLS, TAXES));
    }

    @Test
    public void remove_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.remove(null));
    }

    @Test
    public void remove_expenseDoesNotExist_throwsExpenseNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> expenseList.remove(BILLS));
    }

    @Test
    public void remove_existingExpense_removesExpense() {
        expenseList.add(BILLS);
        expenseList.remove(BILLS);
        ExpenseList expectedExpenseList = new ExpenseList();
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpenses_nullUniqueExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setExpenses((ExpenseList) null));
    }

    @Test
    public void setExpenses_uniqueExpenseList_replacesOwnListWithProvidedUniqueExpenseList() {
        expenseList.add(BILLS);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(TAXES);
        expenseList.setExpenses(expectedExpenseList);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setExpenses((List<Expense>) null));
    }

    @Test
    public void setExpenses_list_replacesOwnListWithProvidedList() {
        expenseList.add(BILLS);
        List<Expense> expenseList = Collections.singletonList(TAXES);
        this.expenseList.setExpenses(expenseList);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(TAXES);
        assertEquals(expectedExpenseList, this.expenseList);
    }

    @Test
    public void setExpenses_listWithDuplicateExpenses_throwsDuplicateExpenseException() {
        List<Expense> listWithDuplicateExpenses = Arrays.asList(BILLS, BILLS);
        assertThrows(DuplicateExpenseException.class, () -> expenseList.setExpenses(listWithDuplicateExpenses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> expenseList.asUnmodifiableObservableList().remove(0));
    }
}
