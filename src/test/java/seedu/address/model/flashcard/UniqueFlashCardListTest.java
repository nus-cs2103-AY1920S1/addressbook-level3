package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashCards.NUS;
import static seedu.address.testutil.TypicalFlashCards.STORE_AND_FORWARD;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.flashcard.exceptions.DuplicateFlashCardException;
import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;
import seedu.address.testutil.FlashCardBuilder;

public class UniqueFlashCardListTest {

    private final UniqueFlashCardList uniqueFlashCardList = new UniqueFlashCardList();

    @Test
    public void contains_nullFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.contains(null));
    }

    @Test
    public void contains_flashCardNotInList_returnsFalse() {
        assertFalse(uniqueFlashCardList.contains(STORE_AND_FORWARD));
    }

    @Test
    public void contains_flashCardInList_returnsTrue() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        assertTrue(uniqueFlashCardList.contains(STORE_AND_FORWARD));
    }

    @Test
    public void contains_flashCardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        FlashCard editedAlice =
                new FlashCardBuilder(STORE_AND_FORWARD)
                        .withRating(VALID_RATING_2)
                        .withCatgeories(VALID_CATEGORY_HISTORY)
                        .build();
        assertTrue(uniqueFlashCardList.contains(editedAlice));
    }

    @Test
    public void add_nullFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.add(null));
    }

    @Test
    public void add_duplicateFlashCard_throwsDuplicateFlashcardException() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        assertThrows(DuplicateFlashCardException.class, () -> uniqueFlashCardList.add(STORE_AND_FORWARD));
    }

    @Test
    public void setFlashCard_nullTargetFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setFlashcard(null, STORE_AND_FORWARD));
    }

    @Test
    public void setFlashCard_nullEditedFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, null));
    }

    @Test
    public void setFlashCard_targetFlashCardNotInList_throwsFlashCardNotFoundException() {
        assertThrows(FlashCardNotFoundException.class, () ->
                uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, STORE_AND_FORWARD));
    }

    @Test
    public void setFlashCard_editedFlashCardIsSameFlashCard_success() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, STORE_AND_FORWARD);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(STORE_AND_FORWARD);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCard_editedFlashCardHasSameIdentity_success() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        FlashCard editedAlice =
                new FlashCardBuilder(STORE_AND_FORWARD)
                        .withRating(VALID_RATING_2)
                        .withCatgeories(VALID_CATEGORY_HISTORY)
                         .build();
        uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, editedAlice);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(editedAlice);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCard_editedFlashCardHasDifferentIdentity_success() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, NUS);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(NUS);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCard_editedFlashCardHasNonUniqueIdentity_throwsDuplicateFlashCardException() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.add(NUS);
        assertThrows(DuplicateFlashCardException.class, () ->
                uniqueFlashCardList.setFlashcard(STORE_AND_FORWARD, NUS));
    }

    @Test
    public void remove_nullFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.remove(null));
    }

    @Test
    public void remove_flashCardDoesNotExist_throwsFlashCardNotFoundException() {
        assertThrows(FlashCardNotFoundException.class, () -> uniqueFlashCardList.remove(STORE_AND_FORWARD));
    }

    @Test
    public void remove_existingFlashCard_removesFlashCard() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        uniqueFlashCardList.remove(STORE_AND_FORWARD);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCards_nullUniqueFlashCardList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setFlashCards((UniqueFlashCardList) null));
    }

    @Test
    public void setFlashCards_uniqueFlashCardList_replacesOwnListWithProvidedUniqueFlashCardList() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(NUS);
        uniqueFlashCardList.setFlashCards(expectedUniqueFlashCardList);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCards_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFlashCardList.setFlashCards((List<FlashCard>) null));
    }

    @Test
    public void setFlashCards_list_replacesOwnListWithProvidedList() {
        uniqueFlashCardList.add(STORE_AND_FORWARD);
        List<FlashCard> flashCardList = Collections.singletonList(NUS);
        uniqueFlashCardList.setFlashCards(flashCardList);
        UniqueFlashCardList expectedUniqueFlashCardList = new UniqueFlashCardList();
        expectedUniqueFlashCardList.add(NUS);
        assertEquals(expectedUniqueFlashCardList, uniqueFlashCardList);
    }

    @Test
    public void setFlashCards_listWithDuplicateFlashCards_throwsDuplicateFlashCardException() {
        List<FlashCard> listWithDuplicateFlashCards = Arrays.asList(STORE_AND_FORWARD, STORE_AND_FORWARD);
        assertThrows(DuplicateFlashCardException.class, () ->
                uniqueFlashCardList.setFlashCards(listWithDuplicateFlashCards));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueFlashCardList.asUnmodifiableObservableList().remove(0));
    }
}
