package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSpendings.ALICE;
import static seedu.address.testutil.TypicalSpendings.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.spending.Spending;
import seedu.address.model.spending.exceptions.DuplicateSpendingException;
import seedu.address.testutil.SpendingBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getSpendingList());
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
    public void resetData_withDuplicateSpendings_throwsDuplicateSpendingException() {
        // Two persons with the same identity fields
        Spending editedAlice = new SpendingBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Spending> newSpendings = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newSpendings);

        assertThrows(DuplicateSpendingException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasSpending_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasSpending(null));
    }

    @Test
    public void hasSpending_spendingNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasSpending(ALICE));
    }

    @Test
    public void hasSpending_spendingInAddressBook_returnsTrue() {
        addressBook.addSpending(ALICE);
        assertTrue(addressBook.hasSpending(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addSpending(ALICE);
        Spending editedAlice = new SpendingBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSpending(editedAlice));
    }

    @Test
    public void getSpendingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getSpendingList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose spendings list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Spending> spendings = FXCollections.observableArrayList();

        AddressBookStub(Collection<Spending> spendings) {
            this.spendings.setAll(spendings);
        }

        @Override
        public ObservableList<Spending> getSpendingList() {
            return spendings;
        }
    }

}
