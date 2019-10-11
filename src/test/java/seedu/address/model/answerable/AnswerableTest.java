package seedu.address.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAnswerables.A_ANSWERABLE;
import static seedu.address.testutil.TypicalAnswerables.BETA;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AnswerableBuilder;

public class AnswerableTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Answerable answerable = new AnswerableBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> answerable.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(A_ANSWERABLE.isSameAnswerable(A_ANSWERABLE));

        // null -> returns false
        assertFalse(A_ANSWERABLE.isSameAnswerable(null));


        // different question -> returns false
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withDifficulty(VALID_DIFFICULTY_BOB).build();
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withQuestion(VALID_QUESTION_BOB).build();
        assertFalse(A_ANSWERABLE.isSameAnswerable(editedAlice));

        // same question, same difficulty, different attributes -> returns true
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategory(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(A_ANSWERABLE.isSameAnswerable(editedAlice));

        // same question, same difficulty, different attributes -> returns true
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(A_ANSWERABLE.isSameAnswerable(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Answerable aliceCopy = new AnswerableBuilder(A_ANSWERABLE).build();
        assertTrue(A_ANSWERABLE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(A_ANSWERABLE.equals(A_ANSWERABLE));

        // null -> returns false
        assertFalse(A_ANSWERABLE.equals(null));

        // different type -> returns false
        assertFalse(A_ANSWERABLE.equals(5));

        // different answerable -> returns false
        assertFalse(A_ANSWERABLE.equals(BETA));

        // different question -> returns false
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withQuestion(VALID_QUESTION_BOB).build();
        assertFalse(A_ANSWERABLE.equals(editedAlice));

        // different difficulty -> returns false
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withDifficulty(VALID_DIFFICULTY_BOB).build();
        assertFalse(A_ANSWERABLE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategory(VALID_ADDRESS_BOB).build();
        assertFalse(A_ANSWERABLE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(A_ANSWERABLE.equals(editedAlice));
    }
}
