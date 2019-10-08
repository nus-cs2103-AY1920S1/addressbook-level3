package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactsList;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.model.contact.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueContactsListTest {

    private final UniqueContactsList uniqueContactsList = new UniqueContactsList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactsList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueContactsList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueContactsList.add(ALICE);
        assertTrue(uniqueContactsList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContactsList.add(ALICE);
        Contact editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueContactsList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactsList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueContactsList.add(ALICE);
        assertThrows(DuplicateContactException.class, () -> uniqueContactsList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactsList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactsList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueContactsList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueContactsList.add(ALICE);
        uniqueContactsList.setPerson(ALICE, ALICE);
        UniqueContactsList expectedUniqueContactsList = new UniqueContactsList();
        expectedUniqueContactsList.add(ALICE);
        assertEquals(expectedUniqueContactsList, uniqueContactsList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueContactsList.add(ALICE);
        Contact editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueContactsList.setPerson(ALICE, editedAlice);
        UniqueContactsList expectedUniqueContactsList = new UniqueContactsList();
        expectedUniqueContactsList.add(editedAlice);
        assertEquals(expectedUniqueContactsList, uniqueContactsList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueContactsList.add(ALICE);
        uniqueContactsList.setPerson(ALICE, BOB);
        UniqueContactsList expectedUniqueContactsList = new UniqueContactsList();
        expectedUniqueContactsList.add(BOB);
        assertEquals(expectedUniqueContactsList, uniqueContactsList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueContactsList.add(ALICE);
        uniqueContactsList.add(BOB);
        assertThrows(DuplicateContactException.class, () -> uniqueContactsList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactsList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueContactsList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueContactsList.add(ALICE);
        uniqueContactsList.remove(ALICE);
        UniqueContactsList expectedUniqueContactsList = new UniqueContactsList();
        assertEquals(expectedUniqueContactsList, uniqueContactsList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactsList.setContacts((UniqueContactsList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueContactsList.add(ALICE);
        UniqueContactsList expectedUniqueContactsList = new UniqueContactsList();
        expectedUniqueContactsList.add(BOB);
        uniqueContactsList.setContacts(expectedUniqueContactsList);
        assertEquals(expectedUniqueContactsList, uniqueContactsList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactsList.setContacts((List<Contact>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueContactsList.add(ALICE);
        List<Contact> contactList = Collections.singletonList(BOB);
        uniqueContactsList.setContacts(contactList);
        UniqueContactsList expectedUniqueContactsList = new UniqueContactsList();
        expectedUniqueContactsList.add(BOB);
        assertEquals(expectedUniqueContactsList, uniqueContactsList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Contact> listWithDuplicateContacts = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateContactException.class, () -> uniqueContactsList.setContacts(listWithDuplicateContacts));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueContactsList.asUnmodifiableObservableList().remove(0));
    }
}
