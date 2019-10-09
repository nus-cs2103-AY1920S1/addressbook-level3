package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashCards.NUS;
import static seedu.address.testutil.TypicalFlashCards.STORE_AND_FORWARD;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FlashCardBuilder;

public class FlashCardTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        FlashCard flashCard = new FlashCardBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> flashCard.getCategories().remove(0));
    }

    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(STORE_AND_FORWARD.isSameFlashCard(STORE_AND_FORWARD));

        // null -> returns false
        assertFalse(STORE_AND_FORWARD.isSameFlashCard(null));

        // different answer -> returns false
        FlashCard editedCard = new FlashCardBuilder(STORE_AND_FORWARD).withAnswer(VALID_ANSWER_2).build();
        assertFalse(STORE_AND_FORWARD.isSameFlashCard(editedCard));

        // different name -> returns false
        editedCard = new FlashCardBuilder(STORE_AND_FORWARD).withQuestion(VALID_QUESTION_2).build();
        assertFalse(STORE_AND_FORWARD.isSameFlashCard(editedCard));

        // same name, different attributes -> returns true
        editedCard = new FlashCardBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2)
                .withCatgeories(VALID_CATEGORY_HISTORY).build();

        assertTrue(STORE_AND_FORWARD.isSameFlashCard(editedCard));

        // same name, same answer, different attributes -> returns true
        editedCard = new FlashCardBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2)
                .withCatgeories(VALID_CATEGORY_HISTORY).build();

        assertTrue(STORE_AND_FORWARD.isSameFlashCard(editedCard));
    }

    @Test
    public void equals() {
        // same values -> returns true
        FlashCard aliceCopy = new FlashCardBuilder(STORE_AND_FORWARD).build();
        assertTrue(STORE_AND_FORWARD.equals(aliceCopy));

        // same object -> returns true
        assertTrue(STORE_AND_FORWARD.equals(STORE_AND_FORWARD));

        // null -> returns false
        assertFalse(STORE_AND_FORWARD.equals(null));

        // different type -> returns false
        assertFalse(STORE_AND_FORWARD.equals(5));

        // different flashCard -> returns false
        assertFalse(STORE_AND_FORWARD.equals(NUS));

        // different name -> returns false
        FlashCard editedAlice = new FlashCardBuilder(STORE_AND_FORWARD).withQuestion(VALID_QUESTION_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different answer -> returns false
        editedAlice = new FlashCardBuilder(STORE_AND_FORWARD).withAnswer(VALID_ANSWER_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));


        // different rating -> returns false
        editedAlice = new FlashCardBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different categories -> returns false
        editedAlice = new FlashCardBuilder(STORE_AND_FORWARD).withCatgeories(VALID_CATEGORY_HISTORY).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));
    }
}
