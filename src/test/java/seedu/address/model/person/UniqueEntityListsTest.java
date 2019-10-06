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

import seedu.address.model.entity.exceptions.DuplicateEntityException;
import seedu.address.model.entity.exceptions.EntityNotFoundException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueEntityListsTest {

    private final UniqueEntityLists uniqueEntityLists = new UniqueEntityLists();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueEntityLists.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueEntityLists.add(ALICE);
        assertTrue(uniqueEntityLists.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEntityLists.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEntityLists.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.add(null));
    }

    @Test
    public void adued_duplicatePerson_throwsDuplicatePersonException() {
        uniqueEntityLists.add(ALICE);
        assertThrows(DuplicateEntityException.class, () -> uniqueEntityLists.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setEntity(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setEntity(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> uniqueEntityLists.setEntity(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueEntityLists.add(ALICE);
        uniqueEntityLists.setEntity(ALICE, ALICE);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(ALICE);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueEntityLists.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEntityLists.setEntity(ALICE, editedAlice);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(editedAlice);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueEntityLists.add(ALICE);
        uniqueEntityLists.setEntity(ALICE, BOB);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(BOB);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEntityLists.add(ALICE);
        uniqueEntityLists.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniqueEntityLists.setEntity(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueEntityLists.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueEntityLists.add(ALICE);
        uniqueEntityLists.remove(ALICE);
        UniqueEntityLists expectedUniqueEntitynList = new UniqueEntityLists();
        assertEquals(expectedUniqueEntitynList, uniqueEntityLists);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setPersons((UniqueEntityLists) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueEntityLists.add(ALICE);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(BOB);
        uniqueEntityLists.setPersons(expectedUniqueEntityList);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueEntityLists.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueEntityLists.setPersons(personList);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(BOB);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueEntityLists.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEntityLists.asUnmodifiableObservableListPerson().remove(0));
    }
}
