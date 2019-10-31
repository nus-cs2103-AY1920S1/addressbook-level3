package tagline.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.contact.TypicalContacts.ALICE;
import static tagline.testutil.contact.TypicalContacts.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tagline.model.contact.exceptions.ContactNotFoundException;
import tagline.model.contact.exceptions.DuplicateContactException;

public class UniqueContactListTest {

    private final UniqueContactList uniqueContactList = new UniqueContactList();

    @Test
    public void containsContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.containsContact(null));
    }

    @Test
    public void containsContact_contactNotInList_returnsFalse() {
        assertFalse(uniqueContactList.containsContact(ALICE));
    }

    @Test
    public void containsContact_contactInList_returnsTrue() {
        uniqueContactList.addContact(ALICE);
        assertTrue(uniqueContactList.containsContact(ALICE));
    }

    @Test
    public void containsContact_contactWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContactList.addContact(ALICE);
        Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(uniqueContactList.containsContact(editedAlice));
    }

    @Test
    public void addContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.addContact(null));
    }

    @Test
    public void addContact_duplicateContact_throwsDuplicateContactException() {
        uniqueContactList.addContact(ALICE);
        assertThrows(DuplicateContactException.class, () -> uniqueContactList.addContact(ALICE));
    }

    @Test
    public void setContact_nullTargetContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContact(null, ALICE));
    }

    @Test
    public void setContact_nullEditedContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContact(ALICE, null));
    }

    @Test
    public void setContact_targetContactNotInList_throwsContactNotFoundException() {
        assertThrows(ContactNotFoundException.class, () -> uniqueContactList.setContact(ALICE, ALICE));
    }

    @Test
    public void setContact_editedContactIsSameContact_success() {
        uniqueContactList.addContact(ALICE);
        uniqueContactList.setContact(ALICE, ALICE);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.addContact(ALICE);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasSameIdentity_success() {
        uniqueContactList.addContact(ALICE);
        Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .build();
        uniqueContactList.setContact(ALICE, editedAlice);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.addContact(editedAlice);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasDifferentIdentity_success() {
        uniqueContactList.addContact(ALICE);
        uniqueContactList.setContact(ALICE, BOB);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.addContact(BOB);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasNonUniqueIdentity_throwsDuplicateContactException() {
        uniqueContactList.addContact(ALICE);
        uniqueContactList.addContact(BOB);
        assertThrows(DuplicateContactException.class, () -> uniqueContactList.setContact(ALICE, BOB));
    }

    @Test
    public void removeContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.removeContact(null));
    }

    @Test
    public void removeContact_contactDoesNotExist_throwsContactNotFoundException() {
        assertThrows(ContactNotFoundException.class, () -> uniqueContactList.removeContact(ALICE));
    }

    @Test
    public void removeContact_existingContact_removesContact() {
        uniqueContactList.addContact(ALICE);
        uniqueContactList.removeContact(ALICE);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullUniqueContactList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContacts((UniqueContactList) null));
    }

    @Test
    public void setContacts_uniqueContactList_replacesOwnListWithProvidedUniqueContactList() {
        uniqueContactList.addContact(ALICE);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.addContact(BOB);
        uniqueContactList.setContacts(expectedUniqueContactList);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContacts((List<Contact>) null));
    }

    @Test
    public void setContacts_list_replacesOwnListWithProvidedList() {
        uniqueContactList.addContact(ALICE);
        List<Contact> contactList = Collections.singletonList(BOB);
        uniqueContactList.setContacts(contactList);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.addContact(BOB);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_listWithDuplicateContacts_throwsDuplicateContactException() {
        List<Contact> listWithDuplicateContacts = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateContactException.class, () -> uniqueContactList.setContacts(listWithDuplicateContacts));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueContactList
                .asUnmodifiableObservableList().remove(0));
    }
}
