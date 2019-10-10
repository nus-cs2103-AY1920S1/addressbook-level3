package seedu.billboard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalPersons.BILLS;
import static seedu.billboard.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.billboard.model.person.Expense;
import seedu.billboard.model.person.Record;
import seedu.billboard.model.person.exceptions.DuplicatePersonException;
import seedu.billboard.testutil.ExpenseBuilder;

public class AddressBookTest {

    private final Billboard addressBook = new Billboard();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Billboard newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two expenses with the same identity fields
        Expense editedAlice = new ExpenseBuilder(BILLS).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Expense> newExpenses = Arrays.asList(BILLS, editedAlice);
        BillboardStub newData = new BillboardStub(newExpenses);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(BILLS));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(BILLS);
        assertTrue(addressBook.hasPerson(BILLS));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(BILLS);
        Expense editedAlice = new ExpenseBuilder(BILLS).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
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
        public ObservableList<Record> getPersonList() {
            return expenses;
        }
    }

}
