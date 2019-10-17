package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalAnswerables.A_ANSWERABLE;
import static seedu.revision.testutil.TypicalAnswerables.BETA;

import org.junit.jupiter.api.Test;

import seedu.revision.testutil.AnswerableBuilder;

public class AnswerableTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Answerable answerable = new AnswerableBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> answerable.getCategories().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(A_ANSWERABLE.isSameAnswerable(A_ANSWERABLE));

        // null -> returns false
        assertFalse(A_ANSWERABLE.isSameAnswerable(null));


        // different question -> returns false
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withDifficulty(VALID_DIFFICULTY_BETA).build();
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withQuestion(VALID_QUESTION_BETA).build();
        assertFalse(A_ANSWERABLE.isSameAnswerable(editedAlice));

        // same question, same difficulty, different attributes -> returns true
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertTrue(A_ANSWERABLE.isSameAnswerable(editedAlice));

        // same question, same difficulty, different attributes -> returns true
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategories(VALID_CATEGORY_GREENFIELD).build();
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
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withQuestion(VALID_QUESTION_BETA).build();
        assertFalse(A_ANSWERABLE.equals(editedAlice));

        // different difficulty -> returns false
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertFalse(A_ANSWERABLE.equals(editedAlice));

        // different categories -> returns false
        editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertFalse(A_ANSWERABLE.equals(editedAlice));
    }
}
