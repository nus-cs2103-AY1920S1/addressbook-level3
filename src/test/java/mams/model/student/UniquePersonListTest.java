package mams.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static mams.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mams.logic.commands.CommandTestUtil;
import mams.model.student.exceptions.DuplicatePersonException;
import mams.model.student.exceptions.PersonNotFoundException;
import mams.testutil.Assert;
import mams.testutil.PersonBuilder;
import mams.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(TypicalPersons.ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(TypicalPersons.ALICE);
        assertTrue(uniquePersonList.contains(TypicalPersons.ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(TypicalPersons.ALICE);
        Assert.assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(TypicalPersons.ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        Assert.assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(TypicalPersons.ALICE, TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(TypicalPersons.ALICE);
        uniquePersonList.setPerson(TypicalPersons.ALICE, TypicalPersons.ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(TypicalPersons.ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(TypicalPersons.ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(TypicalPersons.ALICE);
        uniquePersonList.setPerson(TypicalPersons.ALICE, TypicalPersons.BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(TypicalPersons.BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(TypicalPersons.ALICE);
        uniquePersonList.add(TypicalPersons.BOB);
        Assert.assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(TypicalPersons.ALICE, TypicalPersons.BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        Assert.assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(TypicalPersons.ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(TypicalPersons.ALICE);
        uniquePersonList.remove(TypicalPersons.ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(TypicalPersons.ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(TypicalPersons.BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(TypicalPersons.ALICE);
        List<Person> personList = Collections.singletonList(TypicalPersons.BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(TypicalPersons.BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(TypicalPersons.ALICE, TypicalPersons.ALICE);
        Assert.assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }
}
