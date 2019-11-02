package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalAnswerables.A_ANSWERABLE;
import static seedu.revision.testutil.TypicalAnswerables.BETA;

import org.junit.jupiter.api.Test;

import seedu.revision.testutil.AnswerableBuilder;
import seedu.revision.testutil.McqBuilder;

public class McqTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Answerable answerable = new McqBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> answerable.getCategories().remove(0));
    }

    @Test
    public void isSameAnswerable() {
        // same object -> returns true
        assertTrue(A_ANSWERABLE.isSameAnswerable(A_ANSWERABLE));

        // null -> returns false
        assertFalse(A_ANSWERABLE.isSameAnswerable(null));

        // different question -> returns false
        Answerable editedAnswerable = new McqBuilder(A_ANSWERABLE).withQuestion(VALID_QUESTION_BETA).build();
        assertFalse(A_ANSWERABLE.isSameAnswerable(editedAnswerable));

        // same question, same difficulty, different attributes -> returns true
        editedAnswerable = new McqBuilder(A_ANSWERABLE).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertTrue(A_ANSWERABLE.isSameAnswerable(editedAnswerable));

        // same question, same difficulty, different attributes -> returns true
        editedAnswerable = new McqBuilder(A_ANSWERABLE).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertTrue(A_ANSWERABLE.isSameAnswerable(editedAnswerable));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Answerable answerableCopy = new McqBuilder(A_ANSWERABLE).build();
        assertTrue(A_ANSWERABLE.equals(answerableCopy));

        // same object -> returns true
        assertTrue(A_ANSWERABLE.equals(A_ANSWERABLE));

        assertFalse(A_ANSWERABLE.equals(null));

        // different type -> returns false
        assertFalse(A_ANSWERABLE.equals(5));

        // different answerable -> returns false
        assertFalse(A_ANSWERABLE.equals(BETA));

        // different question -> returns false
        Answerable editedAnswerable = new McqBuilder(A_ANSWERABLE).withQuestion(VALID_QUESTION_BETA).build();
        assertFalse(A_ANSWERABLE.equals(editedAnswerable));

        // different difficulty -> returns false
        editedAnswerable = new McqBuilder(A_ANSWERABLE).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertFalse(A_ANSWERABLE.equals(editedAnswerable));

        // different categories -> returns false
        editedAnswerable = new McqBuilder(A_ANSWERABLE).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertFalse(A_ANSWERABLE.equals(editedAnswerable));
    }
}
