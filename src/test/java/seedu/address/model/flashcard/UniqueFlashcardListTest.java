package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INTELLIJ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashcards.CS_ONE;
import static seedu.address.testutil.TypicalFlashcards.MATH_ONE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.flashcard.exceptions.DuplicateFlashcardException;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardQuestionException;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardTitleException;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;
import seedu.address.testutil.FlashcardBuilder;

public class UniqueFlashcardListTest {
    private final UniqueFlashcardList uniqueFlashcardList = new UniqueFlashcardList();

    @Test
    public void contains_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.contains(null));
    }

    @Test
    public void contains_flashcardNotInList_returnsFalse() {
        assertFalse(uniqueFlashcardList.contains(MATH_ONE));
    }

    @Test
    public void contains_flashcardInList_returnsTrue() {
        uniqueFlashcardList.add(MATH_ONE);
        assertTrue(uniqueFlashcardList.contains(MATH_ONE));
    }

    @Test
    public void contains_flashcardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFlashcardList.add(MATH_ONE);
        Flashcard editedMathOne = new FlashcardBuilder(MATH_ONE)
                .withTitle(VALID_TITLE_ONE)
                .withTags(VALID_TAG_INTELLIJ)
                .build();
        assertTrue(uniqueFlashcardList.contains(editedMathOne));
    }

    @Test
    public void add_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.add(null));
    }

    @Test
    public void add_duplicateFlashcard_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(MATH_ONE);
        assertThrows(DuplicateFlashcardException.class, () -> uniqueFlashcardList.add(MATH_ONE));
    }

    @Test
    public void add_duplicateFlashcardTitle_throwsDuplicateFlashcardTitleException() {
        uniqueFlashcardList.add(MATH_ONE);
        assertThrows(DuplicateFlashcardTitleException.class, () -> uniqueFlashcardList.add(new FlashcardBuilder()
                .withTitle(MATH_ONE.getTitle().toString()).build()));
    }

    @Test
    public void add_duplicateFlashcardQuestion_throwsDuplicateFlashcardQuestionException() {
        uniqueFlashcardList.add(MATH_ONE);
        assertThrows(DuplicateFlashcardQuestionException.class, () -> uniqueFlashcardList.add(new FlashcardBuilder()
                .withQuestion(MATH_ONE.getQuestion().toString()).build()));
    }

    @Test
    public void setFlashcard_nullTargetFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueFlashcardList.setFlashcard(null, MATH_ONE));
    }

    @Test
    public void setFlashcard_nullEditedFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueFlashcardList.setFlashcard(MATH_ONE, null));
    }

    @Test
    public void setFlashcard_targetFlashcardNotInList_throwsFlashcardNotFoundException() {
        assertThrows(FlashcardNotFoundException.class, () -> uniqueFlashcardList.setFlashcard(MATH_ONE, MATH_ONE));
    }

    @Test
    public void setFlashcard_editedFlashcardIsSameFlashcard_success() {
        uniqueFlashcardList.add(MATH_ONE);
        uniqueFlashcardList.setFlashcard(MATH_ONE, MATH_ONE);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(MATH_ONE);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasSameIdentity_success() {
        uniqueFlashcardList.add(MATH_ONE);
        Flashcard editedMathOne = new FlashcardBuilder(MATH_ONE)
                .withTitle(VALID_TITLE_ONE)
                .withTags(VALID_TAG_INTELLIJ)
                .build();
        uniqueFlashcardList.setFlashcard(MATH_ONE, editedMathOne);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(editedMathOne);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasDifferentIdentity_success() {
        uniqueFlashcardList.add(MATH_ONE);
        uniqueFlashcardList.setFlashcard(MATH_ONE, CS_ONE);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(CS_ONE);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasNonUniqueIdentity_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(MATH_ONE);
        uniqueFlashcardList.add(CS_ONE);
        assertThrows(DuplicateFlashcardException.class, () -> uniqueFlashcardList.setFlashcard(MATH_ONE, CS_ONE));
    }

    @Test
    public void setFlashcard_editedFlashcardHasNonUniqueTitle_throwsDuplicateFlashcardTitleException() {
        uniqueFlashcardList.add(MATH_ONE);
        uniqueFlashcardList.add(CS_ONE);
        assertThrows(DuplicateFlashcardTitleException.class, () -> uniqueFlashcardList.setFlashcard(MATH_ONE,
                new FlashcardBuilder().withTitle(CS_ONE.getTitle().toString()).withQuestion("Changed"
                + " question").build()));
    }

    @Test
    public void setFlashcard_editedFlashcardHasNonUniqueQuestion_throwsDuplicateFlashcardQuestionException() {
        uniqueFlashcardList.add(MATH_ONE);
        uniqueFlashcardList.add(CS_ONE);
        assertThrows(DuplicateFlashcardQuestionException.class, () -> uniqueFlashcardList.setFlashcard(MATH_ONE,
                new FlashcardBuilder().withQuestion(CS_ONE.getQuestion().toString()).withTitle("Changed"
                        + " question").build()));
    }

    @Test
    public void remove_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashcardList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(FlashcardNotFoundException.class, () -> uniqueFlashcardList.remove(MATH_ONE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueFlashcardList.add(MATH_ONE);
        uniqueFlashcardList.remove(MATH_ONE);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullUniqueFlashcardList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueFlashcardList.setFlashcards((UniqueFlashcardList) null));
    }

    @Test
    public void setFlashcards_uniqueFlashcardList_replacesOwnListWithProvidedUniqueFlashcardList() {
        uniqueFlashcardList.add(MATH_ONE);
        UniqueFlashcardList expectedUniquePersonList = new UniqueFlashcardList();
        expectedUniquePersonList.add(CS_ONE);
        uniqueFlashcardList.setFlashcards(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueFlashcardList.setFlashcards((List<Flashcard>) null));
    }

    @Test
    public void setFlashcards_list_replacesOwnListWithProvidedList() {
        uniqueFlashcardList.add(MATH_ONE);
        List<Flashcard> personList = Collections.singletonList(CS_ONE);
        uniqueFlashcardList.setFlashcards(personList);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(CS_ONE);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Flashcard> listWithDuplicateFlashcards = Arrays.asList(MATH_ONE, MATH_ONE);
        assertThrows(
                DuplicateFlashcardException.class, (
                ) -> uniqueFlashcardList.setFlashcards(listWithDuplicateFlashcards));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, (
                ) -> uniqueFlashcardList.asUnmodifiableObservableList().remove(0));
    }
}
