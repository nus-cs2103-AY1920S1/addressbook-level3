package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.ANNIVERSARY;
import static seedu.address.testutil.TypicalExpenses.TRANSPORT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;

public class UniqueExpenseListTest {

    private final UniqueExpenseList uniqueExpenseList = new UniqueExpenseList();

    @Test
    public void contains_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.contains(null));
    }

    @Test
    public void contains_expenseNotInList_returnsFalse() {
        assertFalse(uniqueExpenseList.contains(ANNIVERSARY));
    }

    @Test
    public void contains_expenseInList_returnsTrue() {
        uniqueExpenseList.add(ANNIVERSARY);
        assertTrue(uniqueExpenseList.contains(ANNIVERSARY));
    }

    @Test
    public void add_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.add(null));
    }

    @Test
    public void add_duplicateExpense_throwsDuplicateExpenseException() {
        uniqueExpenseList.add(ANNIVERSARY);
        assertThrows(DuplicateExpenseException.class, () -> uniqueExpenseList.add(ANNIVERSARY));
    }

    @Test
    public void setExpense_nullTargetExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setExpense(null, ANNIVERSARY));
    }

    @Test
    public void setExpense_nullEditedExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setExpense(ANNIVERSARY, null));
    }

    @Test
    public void setExpense_targetExpenseNotInList_throwsExpenseNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> uniqueExpenseList.setExpense(ANNIVERSARY, ANNIVERSARY));
    }

    @Test
    public void setExpense_editedExpenseIsSameExpense_success() {
        uniqueExpenseList.add(ANNIVERSARY);
        uniqueExpenseList.setExpense(ANNIVERSARY, ANNIVERSARY);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(ANNIVERSARY);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }
    //TODO: include some sort of expense ID for expense equality
    //@Test
    //public void setExpense_editedExpenseHasSameIdentity_success() {
    //    uniqueExpenseList.add(ANNIVERSARY);
    //    Expense editedAlice = new ExpenseBuilder(ANNIVERSARY)
    //            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_CLAIMABLE)
    //            .build();
    //    uniqueExpenseList.setExpense(ANNIVERSARY, editedAlice);
    //    UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
    //    expectedUniqueExpenseList.add(editedAlice);
    //    assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    //}

    @Test
    public void setExpense_editedExpenseHasDifferentIdentity_success() {
        uniqueExpenseList.add(ANNIVERSARY);
        uniqueExpenseList.setExpense(ANNIVERSARY, TRANSPORT);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(TRANSPORT);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpense_editedExpenseHasNonUniqueIdentity_throwsDuplicateExpenseException() {
        uniqueExpenseList.add(ANNIVERSARY);
        uniqueExpenseList.add(TRANSPORT);
        assertThrows(DuplicateExpenseException.class, () -> uniqueExpenseList.setExpense(ANNIVERSARY, TRANSPORT));
    }

    @Test
    public void remove_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.remove(null));
    }

    @Test
    public void remove_expenseDoesNotExist_throwsExpenseNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> uniqueExpenseList.remove(ANNIVERSARY));
    }

    @Test
    public void remove_existingExpense_removesExpense() {
        uniqueExpenseList.add(ANNIVERSARY);
        uniqueExpenseList.remove(ANNIVERSARY);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpenses_nullUniqueExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setExpenses((UniqueExpenseList) null));
    }

    @Test
    public void setExpenses_uniqueExpenseList_replacesOwnListWithProvidedUniqueExpenseList() {
        uniqueExpenseList.add(ANNIVERSARY);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(TRANSPORT);
        uniqueExpenseList.setExpenses(expectedUniqueExpenseList);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setExpenses((List<Expense>) null));
    }

    @Test
    public void setExpenses_list_replacesOwnListWithProvidedList() {
        uniqueExpenseList.add(ANNIVERSARY);
        List<Expense> expenseList = Collections.singletonList(TRANSPORT);
        uniqueExpenseList.setExpenses(expenseList);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(TRANSPORT);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpenses_listWithDuplicateExpenses_throwsDuplicateExpenseException() {
        List<Expense> listWithDuplicateExpenses = Arrays.asList(ANNIVERSARY, ANNIVERSARY);
        assertThrows(DuplicateExpenseException.class, () -> uniqueExpenseList.setExpenses(listWithDuplicateExpenses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueExpenseList.asUnmodifiableObservableList().remove(0));
    }
}
