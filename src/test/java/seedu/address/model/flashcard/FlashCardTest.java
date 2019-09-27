package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.STORE_AND_FORWARD;
import static seedu.address.testutil.TypicalPersons.NUS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FlashCardTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        FlashCard flashCard = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> flashCard.getCategories().remove(0));
    }

    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(STORE_AND_FORWARD.isSamePerson(STORE_AND_FORWARD));

        // null -> returns false
        assertFalse(STORE_AND_FORWARD.isSamePerson(null));

        // different answer -> returns false
        FlashCard editedSTORE_AND_FORWARD = new PersonBuilder(STORE_AND_FORWARD).withAnswer(VALID_ANSWER_2).build();
        assertFalse(STORE_AND_FORWARD.isSamePerson(editedSTORE_AND_FORWARD));

        // different name -> returns false
        editedSTORE_AND_FORWARD = new PersonBuilder(STORE_AND_FORWARD).withQuestion(VALID_QUESTION_2).build();
        assertFalse(STORE_AND_FORWARD.isSamePerson(editedSTORE_AND_FORWARD));

        // same name, different attributes -> returns true
        editedSTORE_AND_FORWARD = new PersonBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2)
                .withTags(VALID_CATEGORY_HISTORY).build();

        assertTrue(STORE_AND_FORWARD.isSamePerson(editedSTORE_AND_FORWARD));

        // same name, same answer, different attributes -> returns true
        editedSTORE_AND_FORWARD = new PersonBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2)
                .withTags(VALID_CATEGORY_HISTORY).build();

        assertTrue(STORE_AND_FORWARD.isSamePerson(editedSTORE_AND_FORWARD));
    }

    @Test
    public void equals() {
        // same values -> returns true
        FlashCard aliceCopy = new PersonBuilder(STORE_AND_FORWARD).build();
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
        FlashCard editedAlice = new PersonBuilder(STORE_AND_FORWARD).withQuestion(VALID_QUESTION_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different answer -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withAnswer(VALID_ANSWER_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));


        // different rating -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withRating(VALID_RATING_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different categories -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withTags(VALID_CATEGORY_HISTORY).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));
    }
}
