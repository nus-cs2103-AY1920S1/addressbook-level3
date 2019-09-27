package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.STORE_AND_FORWARD;
import static seedu.address.testutil.TypicalPersons.NUS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.flashcard.exceptions.DuplicatePersonException;
import seedu.address.model.flashcard.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueFlashCardListTest {

    private final UniqueFlashCardList uniqueFlashCardList = new UniqueFlashCardList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueFlashCardList.contains(STORE_AND_FORWARD));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        assertTrue(uniqueFlashCardList.contains(STORE_AND_FORWARD));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        FlashCard editedAlice = new PersonBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2).withTags(VALID_CATEGORY_HISTORY)
                .build();
        assertTrue(uniqueFlashCardList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        assertThrows(DuplicatePersonException.class, () -> uniqueFlashCardList.add(STORE_AND_FORWARD));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setFlashcard(null, STORE_AND_FORWARD));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, STORE_AND_FORWARD));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, STORE_AND_FORWARD);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(STORE_AND_FORWARD);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        FlashCard editedAlice = new PersonBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2).withTags(VALID_CATEGORY_HISTORY)
                .build();
        uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, editedAlice);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(editedAlice);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, NUS);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(NUS);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.add(NUS);
        assertThrows(DuplicatePersonException.class, () -> uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, NUS));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueFlashCardList.remove(STORE_AND_FORWARD));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.remove(STORE_AND_FORWARD);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setPersons((UniqueFlashCardList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(NUS);
        uniqueFlashCardList.setPersons(expectedUniqueFlashCardList);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setPersons((List<FlashCard>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        List<FlashCard> flashCardList = Collections.singletonList(NUS);
        uniqueFlashCardList.setPersons(flashCardList);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(NUS);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<FlashCard> listWithDuplicateFlashCards = Arrays.asList(STORE_AND_FORWARD, STORE_AND_FORWARD);
        assertThrows(DuplicatePersonException.class, () -> uniqueFlashCardList.setPersons(listWithDuplicateFlashCards));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFlashCardList.asUnmodifiableObservableList().remove(0));
    }
}
