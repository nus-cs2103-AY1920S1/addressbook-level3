package seedu.billboard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.billboard.model.person.Expense;
import seedu.billboard.model.person.exceptions.DuplicatePersonException;
import seedu.billboard.testutil.ExpenseBuilder;

public class AddressBookTest {

    private final Billboard addressBook = new Billboard();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getExpenses());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Billboard newData = getTypicalBillboard();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two expenses with the same identity fields
        Expense editedAlice = new ExpenseBuilder(BILLS).withAmount(VALID_AMOUNT_TAXES).withTags(VALID_TAG_DINNER)
                .build();
        List<Expense> newExpenses = Arrays.asList(BILLS, editedAlice);
        BillboardStub newData = new BillboardStub(newExpenses);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasExpense(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasExpense(BILLS));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addExpense(BILLS);
        assertTrue(addressBook.hasExpense(BILLS));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addExpense(BILLS);
        Expense editedAlice = new ExpenseBuilder(BILLS).withAmount(VALID_AMOUNT_TAXES).withTags(VALID_TAG_DINNER)
                .build();
        assertTrue(addressBook.hasExpense(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getExpenses().remove(0));
    }

    /**
     * A stub ReadOnlyBillboard whose expenses list can violate interface constraints.
     */
    private static class BillboardStub implements ReadOnlyBillboard {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

        BillboardStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenses() {
            return expenses;
        }
    }

}
