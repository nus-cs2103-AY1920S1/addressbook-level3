package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CLAIMABLE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.ANNIVERSARY;
import static seedu.address.testutil.TypicalExpenses.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.testutil.ExpenseBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getExpenseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateExpenses_throwsDuplicateExpenseException() {
        // Two expenses with the same identity fields
        Expense editedAlice = new ExpenseBuilder(ANNIVERSARY).withTags(VALID_TAG_CLAIMABLE)
                .build();
        List<Expense> newExpenses = Arrays.asList(ANNIVERSARY, editedAlice);
        AddressBookStub newData = new AddressBookStub(newExpenses);

        assertThrows(DuplicateExpenseException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasExpense(null));
    }

    @Test
    public void hasExpense_expenseNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasExpense(ANNIVERSARY));
    }

    @Test
    public void hasExpense_expenseInAddressBook_returnsTrue() {
        addressBook.addExpense(ANNIVERSARY);
        assertTrue(addressBook.hasExpense(ANNIVERSARY));
    }

    //@Test
    //public void hasExpense_expenseWithSameIdentityFieldsInAddressBook_returnsTrue() {
    //    addressBook.addExpense(ANNIVERSARY);
    //    Expense editedAlice = new ExpenseBuilder(ANNIVERSARY)
    //            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_CLAIMABLE)
    //            .build();
    //    assertTrue(addressBook.hasExpense(editedAlice));
    //}

    @Test
    public void getExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getExpenseList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose expenses list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
        private final ObservableList<Budget> budgets = FXCollections.observableArrayList();

        AddressBookStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }
    }

}
