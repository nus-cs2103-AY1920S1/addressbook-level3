package seedu.address.model.quiz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.quiz.TypicalSavedQuizzes;

public class QuizTest {

    private static final Quiz EMPTY_QUIZ = new Quiz("empty");
    private static final Quiz QUIZ_TWO = TypicalSavedQuizzes.QUIZ2;
    private static final Quiz QUIZ_THREE = TypicalSavedQuizzes.QUIZ3;

    @Test
    public void sameName_consideredEquals() {
        assertTrue(QUIZ_TWO.equals(QUIZ_TWO));
        assertTrue(QUIZ_THREE.equals(QUIZ_THREE));
        assertTrue(EMPTY_QUIZ.equals(EMPTY_QUIZ));
    }

    @Test
    public void differentName_consideredUnique() {
        assertFalse(QUIZ_TWO.equals(QUIZ_THREE));
        assertFalse(QUIZ_THREE.equals(EMPTY_QUIZ));
        assertFalse(QUIZ_TWO.equals(EMPTY_QUIZ));
    }

}
