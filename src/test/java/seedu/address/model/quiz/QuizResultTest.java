package seedu.address.model.quiz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.FINISH_TIME_ALGEBRA;
import static seedu.address.testutil.TypicalAppData.CORRECT_CONCEPT_RESULT;
import static seedu.address.testutil.TypicalAppData.INCORRECT_CONCEPT_RESULT;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.QuizResultBuilder;

class QuizResultTest {

    @Test
    public void equals() {
        // same object -> return true
        assertTrue(CORRECT_CONCEPT_RESULT.equals(CORRECT_CONCEPT_RESULT));

        // same value -> return true
        QuizResult resultCopy = new QuizResultBuilder(CORRECT_CONCEPT_RESULT).build();
        assertTrue(CORRECT_CONCEPT_RESULT.equals(resultCopy));

        // null -> return false
        assertFalse(CORRECT_CONCEPT_RESULT.equals(null));

        // different time -> return false
        QuizResult editedConcept = new QuizResultBuilder(INCORRECT_CONCEPT_RESULT)
                .withQuizTime(FINISH_TIME_ALGEBRA)
                .build();
        assertFalse(INCORRECT_CONCEPT_RESULT.equals(editedConcept));
    }
}
