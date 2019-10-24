package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashCards.STORE_AND_FORWARD;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.category.Category;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.exceptions.DuplicateFlashCardException;
import seedu.address.testutil.FlashCardBuilder;

public class KeyboardFlashCardsTest {

    private final KeyboardFlashCards keyboardFlashCards = new KeyboardFlashCards();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), keyboardFlashCards.getFlashcardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> keyboardFlashCards.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        KeyboardFlashCards newData = getTypicalAddressBook();
        keyboardFlashCards.resetData(newData);
        assertEquals(newData, keyboardFlashCards);
    }

    @Test
    public void resetData_withDuplicateFlashCards_throwsDuplicateFlashCardException() {
        // Two flashCards with the same identity fields
        FlashCard editedCard =
                new FlashCardBuilder(STORE_AND_FORWARD)
                        .withRating(VALID_RATING_2)
                        .withCatgeories(VALID_CATEGORY_HISTORY)
                        .build();
        List<FlashCard> newFlashCards = Arrays.asList(STORE_AND_FORWARD, editedCard);
        KeyboardFlashCardsStub newData = new KeyboardFlashCardsStub(newFlashCards);

        assertThrows(DuplicateFlashCardException.class, () -> keyboardFlashCards.resetData(newData));
    }

    @Test
    public void hasFlashCard_nullFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> keyboardFlashCards.hasFlashcard(null));
    }

    @Test
    public void hasFlashCard_flashCardNotInAddressBook_returnsFalse() {
        assertFalse(keyboardFlashCards.hasFlashcard(STORE_AND_FORWARD));
    }

    @Test
    public void hasFlashCard_flashCardInAddressBook_returnsTrue() {
        keyboardFlashCards.addFlashcard(STORE_AND_FORWARD);
        assertTrue(keyboardFlashCards.hasFlashcard(STORE_AND_FORWARD));
    }

    @Test
    public void hasFlashCard_flashCardWithSameIdentityFieldsInAddressBook_returnsTrue() {
        keyboardFlashCards.addFlashcard(STORE_AND_FORWARD);
        FlashCard editedAlice =
                new FlashCardBuilder(STORE_AND_FORWARD)
                        .withRating(VALID_RATING_2)
                        .withCatgeories(VALID_CATEGORY_HISTORY)
                .build();
        assertTrue(keyboardFlashCards.hasFlashcard(editedAlice));
    }

    @Test
    public void getFlashCardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> keyboardFlashCards.getFlashcardList().remove(0));
    }

    /**
     * A stub ReadOnlyKeyboardFlashCards whose flashCards list can violate interface constraints.
     */
    private static class KeyboardFlashCardsStub implements ReadOnlyKeyboardFlashCards {
        private final ObservableList<FlashCard> flashCards = FXCollections.observableArrayList();

        KeyboardFlashCardsStub(Collection<FlashCard> flashCards) {
            this.flashCards.setAll(flashCards);
        }

        @Override
        public ObservableList<FlashCard> getFlashcardList() {
            return flashCards;
        }

        @Override
        public ObservableList<Deadline> getDeadlineList() {
            return null;
        }

        @Override
        public ObservableList<Category> getCategoryList() {
            return null;
        }
    }

}
