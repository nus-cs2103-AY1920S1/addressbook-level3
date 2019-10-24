package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEateries.ALICE;
import static seedu.address.testutil.TypicalEateries.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.exceptions.DuplicateEateryException;
import seedu.address.testutil.EateryBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getEateryList());
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
    public void resetData_withDuplicateEateries_throwsDuplicateEateryException() {
        // Two eateries with the same identity fields
        Eatery editedAlice = new EateryBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Eatery> newEateries = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newEateries);

        assertThrows(DuplicateEateryException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasEatery_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasEatery(null));
    }

    @Test
    public void hasEatery_eateryNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEatery(ALICE));
    }

    @Test
    public void hasEatery_eateryInAddressBook_returnsTrue() {
        addressBook.addEatery(ALICE);
        assertTrue(addressBook.hasEatery(ALICE));
    }

    @Test
    public void hasEatery_eateryWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEatery(ALICE);
        Eatery editedAlice = new EateryBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasEatery(editedAlice));
    }

    @Test
    public void getEateryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getEateryList().remove(0));
    }
    /**
     * A stub ReadOnlyAddressBook whose eateries list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Eatery> eateries = FXCollections.observableArrayList();
        private final ObservableList<Eatery> todos = FXCollections.observableArrayList();

        AddressBookStub(Collection<Eatery> eateries) {
            this.eateries.setAll(eateries);
        }

        @Override
        public ObservableList<Eatery> getEateryList() {
            return eateries;
        }

        @Override
        public ObservableList<Eatery> getTodoList() {
            return todos;
        }
    }

}
