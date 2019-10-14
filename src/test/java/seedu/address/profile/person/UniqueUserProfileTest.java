package seedu.address.profile.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DENGUE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.profile.person.exceptions.DuplicatePersonException;
import seedu.address.profile.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueUserProfileTest {

    private final UniqueUserProfile uniqueUserProfile = new UniqueUserProfile();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueUserProfile.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueUserProfile.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueUserProfile.add(ALICE);
        assertTrue(uniqueUserProfile.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueUserProfile.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withMedicalHistories(VALID_HISTORY_DENGUE)
                .build();
        assertTrue(uniqueUserProfile.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueUserProfile.add(null));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueUserProfile.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueUserProfile.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueUserProfile.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueUserProfile.add(ALICE);
        uniqueUserProfile.setPerson(ALICE, ALICE);
        UniqueUserProfile expectedUniqueUserProfile = new UniqueUserProfile();
        expectedUniqueUserProfile.add(ALICE);
        assertEquals(expectedUniqueUserProfile, uniqueUserProfile);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueUserProfile.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withMedicalHistories(VALID_HISTORY_DENGUE)
                .build();
        uniqueUserProfile.setPerson(ALICE, editedAlice);
        UniqueUserProfile expectedUniqueUserProfile = new UniqueUserProfile();
        expectedUniqueUserProfile.add(editedAlice);
        assertEquals(expectedUniqueUserProfile, uniqueUserProfile);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueUserProfile.add(ALICE);
        uniqueUserProfile.setPerson(ALICE, BOB);
        UniqueUserProfile expectedUniqueUserProfile = new UniqueUserProfile();
        expectedUniqueUserProfile.add(BOB);
        assertEquals(expectedUniqueUserProfile, uniqueUserProfile);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueUserProfile.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueUserProfile.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueUserProfile.add(ALICE);
        uniqueUserProfile.remove(ALICE);
        UniqueUserProfile expectedUniqueUserProfile = new UniqueUserProfile();
        assertEquals(expectedUniqueUserProfile, uniqueUserProfile);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueUserProfile.setPersons((UniqueUserProfile) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueUserProfile.add(ALICE);
        UniqueUserProfile expectedUniqueUserProfile = new UniqueUserProfile();
        expectedUniqueUserProfile.add(BOB);
        uniqueUserProfile.setPersons(expectedUniqueUserProfile);
        assertEquals(expectedUniqueUserProfile, uniqueUserProfile);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueUserProfile.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueUserProfile.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueUserProfile.setPersons(personList);
        UniqueUserProfile expectedUniqueUserProfile = new UniqueUserProfile();
        expectedUniqueUserProfile.add(BOB);
        assertEquals(expectedUniqueUserProfile, uniqueUserProfile);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueUserProfile.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueUserProfile.asUnmodifiableObservableList().remove(0));
    }
}
