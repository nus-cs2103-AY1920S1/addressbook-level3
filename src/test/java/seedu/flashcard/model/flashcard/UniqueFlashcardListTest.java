package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;
import static seedu.flashcard.testutil.TypicalFlashcard.DAXING_AIRPORT;
import static seedu.flashcard.testutil.TypicalFlashcard.MOUNT_BLANC;

import org.junit.jupiter.api.Test;

import seedu.flashcard.model.flashcard.exceptions.CardNotFoundException;
import seedu.flashcard.model.flashcard.exceptions.DuplicateCardException;
import seedu.flashcard.testutil.FlashcardBuilder;

public class UniqueFlashcardListTest {

    private final UniqueFlashcardList uniqueFlashcardList = new UniqueFlashcardList();

    @Test
    public void contains_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.contains(null));
    }

    @Test
    public void contains_flashcardNotInList_returnsFalse() {
        assertFalse(uniqueFlashcardList.contains(MOUNT_BLANC));
    }

    @Test
    public void contains_flashcardInList_returnsTrue() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        assertTrue(uniqueFlashcardList.contains(MOUNT_BLANC));
    }

    @Test
    public void add_flashcardWithSameQuestionInList_returnsTrue() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        Flashcard editedFlashcard =
            new FlashcardBuilder((McqFlashcard) MOUNT_BLANC).withDefinition("123").buildMcqFlashcard();
        assertTrue(uniqueFlashcardList.contains(editedFlashcard));
    }

    @Test
    public void add_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.add(null));
    }

    @Test
    public void add_duplicateFlashcard_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        assertThrows(DuplicateCardException.class, () -> uniqueFlashcardList.add(MOUNT_BLANC));
    }

    @Test
    public void setFlashcard_nullTargetFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.setFlashcard(null, MOUNT_BLANC));
    }

    @Test
    public void setFlashcard_nullEditedFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.setFlashcard(MOUNT_BLANC, null));
    }

    @Test
    public void setFlashcard_targetFlashcardNotInList_throwsCardNotFoundException() {
        assertThrows(CardNotFoundException.class, () -> uniqueFlashcardList.setFlashcard(MOUNT_BLANC, MOUNT_BLANC));
    }

    @Test
    public void setFlashcard_editedFlashcardIsSameFlashcard_success() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        uniqueFlashcardList.setFlashcard(MOUNT_BLANC, MOUNT_BLANC);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(MOUNT_BLANC);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasSameQuestion_success() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        Flashcard editedFlashcard = new FlashcardBuilder((McqFlashcard) MOUNT_BLANC)
            .withAnswer("tall").withDefinition("123").buildMcqFlashcard();
        uniqueFlashcardList.setFlashcard(MOUNT_BLANC, editedFlashcard);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(editedFlashcard);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasDifferentQuestion_success() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        uniqueFlashcardList.setFlashcard(MOUNT_BLANC, DAXING_AIRPORT);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(DAXING_AIRPORT);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasNonUniqueQuestion_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        uniqueFlashcardList.add(DAXING_AIRPORT);
        assertThrows(DuplicateCardException.class, () -> uniqueFlashcardList.setFlashcard(MOUNT_BLANC, DAXING_AIRPORT));
    }

    @Test
    public void remove_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.remove(null));
    }

    @Test
    public void remove_flashcardDoesNotExist_throwsCardNotFoundException() {
        assertThrows(CardNotFoundException.class, () -> uniqueFlashcardList.remove(MOUNT_BLANC));
    }

    @Test
    public void remove_existingFlashcard_removeFlashcard() {
        uniqueFlashcardList.add(DAXING_AIRPORT);
        uniqueFlashcardList.remove(DAXING_AIRPORT);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullUniqueFlashcardList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.setFlashcards((UniqueFlashcardList) null));
    }

    @Test
    public void setFlashcards_uniqueFlashcardList_replaceOwnList() {
        uniqueFlashcardList.add(MOUNT_BLANC);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(DAXING_AIRPORT);
        uniqueFlashcardList.setFlashcards(expectedUniqueFlashcardList);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }
}
