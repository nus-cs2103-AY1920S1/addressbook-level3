package seedu.address.model.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.diary.exceptions.DuplicateDiaryException;
import seedu.address.model.diary.exceptions.DiaryNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueDiaryListTest {

    private final UniqueDiaryList uniqueDiaryList = new UniqueDiaryList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDiaryList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueDiaryList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueDiaryList.add(ALICE);
        assertTrue(uniqueDiaryList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDiaryList.add(ALICE);
        Diary editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(uniqueDiaryList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDiaryList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueDiaryList.add(ALICE);
        assertThrows(DuplicateDiaryException.class, () -> uniqueDiaryList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiary(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiary(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(DiaryNotFoundException.class, () -> uniqueDiaryList.setDiary(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueDiaryList.add(ALICE);
        uniqueDiaryList.setDiary(ALICE, ALICE);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(ALICE);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueDiaryList.add(ALICE);
        Diary editedAlice = new PersonBuilder(ALICE).build();
        uniqueDiaryList.setDiary(ALICE, editedAlice);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(editedAlice);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueDiaryList.add(ALICE);
        uniqueDiaryList.setDiary(ALICE, BOB);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(BOB);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueDiaryList.add(ALICE);
        uniqueDiaryList.add(BOB);
        assertThrows(DuplicateDiaryException.class, () -> uniqueDiaryList.setDiary(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDiaryList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(DiaryNotFoundException.class, () -> uniqueDiaryList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueDiaryList.add(ALICE);
        uniqueDiaryList.remove(ALICE);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiaries((UniqueDiaryList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueDiaryList.add(ALICE);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(BOB);
        uniqueDiaryList.setDiaries(expectedUniqueDiaryList);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDiaryList.setDiaries((List<Diary>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueDiaryList.add(ALICE);
        List<Diary> diaryList = Collections.singletonList(BOB);
        uniqueDiaryList.setDiaries(diaryList);
        UniqueDiaryList expectedUniqueDiaryList = new UniqueDiaryList();
        expectedUniqueDiaryList.add(BOB);
        assertEquals(expectedUniqueDiaryList, uniqueDiaryList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Diary> listWithDuplicateDiaries = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateDiaryException.class, () -> uniqueDiaryList.setDiaries(listWithDuplicateDiaries));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDiaryList.asUnmodifiableObservableList().remove(0));
    }
}
