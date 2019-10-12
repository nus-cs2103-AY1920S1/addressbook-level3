package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.getTypicalFinSec;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.model.income.Income;
import seedu.address.testutil.ContactBuilder;

public class FinSecTest {

    private final FinSec finSec = new FinSec();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), finSec.getContactList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        FinSec newData = getTypicalFinSec();
        finSec.resetData(newData);
        assertEquals(newData, finSec);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two contacts with the same identity fields
        seedu.address.model.contact.Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<seedu.address.model.contact.Contact> newContacts = Arrays.asList(ALICE, editedAlice);
        ContactStub newData = new ContactStub(newContacts);

        assertThrows(DuplicateContactException.class, () -> finSec.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.hasContact(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(finSec.hasContact(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        finSec.addContact(ALICE);
        assertTrue(finSec.hasContact(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        finSec.addContact(ALICE);
        seedu.address.model.contact.Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(finSec.hasContact(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> finSec.getContactList().remove(0));
    }

    /**
     * A stub ReadOnlyContact whose contacts list can violate interface constraints.
     */
    private static class ContactStub implements ReadOnlyFinSec {

        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
        private final ObservableList<Income> incomes = FXCollections.observableArrayList();
        private final ObservableList<Claim> claims = FXCollections.observableArrayList();

        ContactStub(Collection<Contact> contacts) {
            this.contacts.setAll(contacts);
        }

        @Override
        public ObservableList<seedu.address.model.contact.Contact> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Income> getIncomeList() {
            return incomes;
        }

        @Override
        public ObservableList<Claim> getClaimList() { return claims; }
    }

}
