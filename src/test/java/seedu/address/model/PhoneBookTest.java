package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPhones.IPHONEXR;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.exceptions.DuplicatePhoneException;
import seedu.address.testutil.PhoneBuilder;

public class PhoneBookTest {

    private final PhoneBook phoneBook = new PhoneBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), phoneBook.getList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> phoneBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDataBookPhone_replacesData() {
        PhoneBook newData = getTypicalPhoneBook();
        phoneBook.resetData(newData);
        assertEquals(newData, phoneBook);
    }

    @Test
    public void resetData_withDuplicatePhones_throwsDuplicatePhoneException() {
        // Two phones with the same id
        Phone editediPhoneXr = new PhoneBuilder(IPHONEXR, true)
                .build();
        List<Phone> newPhones = Arrays.asList(IPHONEXR, editediPhoneXr);
        PhoneBookStub newData = new PhoneBookStub(newPhones);

        assertThrows(DuplicatePhoneException.class, () -> phoneBook.resetData(newData));
    }

    @Test
    public void hasPhone_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> phoneBook.hasPhone(null));
    }

    @Test
    public void hasPhone_phoneNotInPhoneBook_returnsFalse() {
        assertFalse(phoneBook.hasPhone(IPHONEXR));
    }

    @Test
    public void hasPhone_phoneWithSameIdentityFieldsInPhoneBook_returnsTrue() {
        phoneBook.addPhone(IPHONEXR);
        Phone editediPhoneXr = new PhoneBuilder(IPHONEXR, true).withColour("Purple")
                .build();
        assertTrue(phoneBook.hasPhone(editediPhoneXr));
    }

    @Test
    public void getPhoneList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> phoneBook.getList().remove(0));
    }

    /**
     * A stub ReadOnlyDataBook(Phone) whose phones list can violate interface constraints.
     */
    private static class PhoneBookStub implements ReadOnlyDataBook<Phone> {
        private final ObservableList<Phone> phones = FXCollections.observableArrayList();

        PhoneBookStub(Collection<Phone> phones) {
            this.phones.setAll(phones);
        }

        @Override
        public ObservableList<Phone> getList() {
            return phones;
        }
    }

}
