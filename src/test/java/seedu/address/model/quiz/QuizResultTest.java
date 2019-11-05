package seedu.address.model.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.FINISH_TIME_ALGEBRA;
import static seedu.address.testutil.TypicalAppData.CORRECT_CONCEPT_RESULT;
import static seedu.address.testutil.TypicalAppData.INCORRECT_CONCEPT_RESULT;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.QuizResultBuilder;

class QuizResultTest {
    @Test
    void equals() {
        // same object -> return true
        assertEquals(CORRECT_CONCEPT_RESULT, CORRECT_CONCEPT_RESULT);

        // same value -> return true
        QuizResult resultCopy = new QuizResultBuilder(CORRECT_CONCEPT_RESULT).build();
        assertEquals(CORRECT_CONCEPT_RESULT, resultCopy);

        // null -> return false
        assertNotEquals(null, CORRECT_CONCEPT_RESULT);

        // different time -> return false
        QuizResult editedConcept = new QuizResultBuilder(INCORRECT_CONCEPT_RESULT)
                .withQuizTime(FINISH_TIME_ALGEBRA)
                .build();
        assertNotEquals(INCORRECT_CONCEPT_RESULT, editedConcept);
    }
}
