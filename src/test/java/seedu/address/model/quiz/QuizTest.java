package seedu.address.model.quiz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.quiz.QuizBuilder;
import seedu.address.testutil.quiz.TypicalSavedQuizzes;

/**
 * Test for the Quiz Model.
 */
public class QuizTest {

    private static final Quiz EMPTY_QUIZ = new Quiz("empty");
    private static final Quiz QUIZ_ONE = TypicalSavedQuizzes.QUIZ_ONE;
    private static final Quiz QUIZ_TWO = TypicalSavedQuizzes.QUIZ_TWO;

    @Test
    public void sameName_consideredEquals() {
        assertTrue(QUIZ_ONE.equals(QUIZ_ONE));
        assertTrue(QUIZ_TWO.equals(QUIZ_TWO));
        assertTrue(EMPTY_QUIZ.equals(EMPTY_QUIZ));
    }

    @Test
    public void sameName_differentQuestions_consideredEquals() {
        Quiz editedQuizOne = new QuizBuilder(QUIZ_ONE).withQuizId("empty").build();
        assertTrue(editedQuizOne.equals(EMPTY_QUIZ));
        Quiz editedQuizTwo = new QuizBuilder(QUIZ_TWO).withQuizId("empty").build();
        assertTrue(editedQuizTwo.equals(EMPTY_QUIZ));
    }

    @Test
    public void differentName_consideredUnique() {
        assertFalse(QUIZ_ONE.equals(QUIZ_TWO));
        assertFalse(QUIZ_TWO.equals(EMPTY_QUIZ));
        assertFalse(QUIZ_ONE.equals(EMPTY_QUIZ));
    }

    @Test
    public void differentName_sameQuestions_consideredUnique() {
        Quiz editedQuiz = new QuizBuilder(QUIZ_TWO).withQuizId("EditedGroup").build();
        assertFalse(editedQuiz.equals(QUIZ_TWO));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Quiz quiz1Copy = new QuizBuilder(QUIZ_ONE).build();
        assertTrue(quiz1Copy.equals(QUIZ_ONE));

        // same object -> returns true
        assertTrue(QUIZ_ONE.equals(QUIZ_ONE));

        // null -> returns false
        assertFalse(QUIZ_ONE.equals(null));

        // different type -> returns false
        assertFalse(QUIZ_ONE.equals(5));

        // different quiz -> returns false
        assertFalse(QUIZ_ONE.equals(QUIZ_TWO));

        // different quiz id -> returns false
        Quiz editedQuiz1 = new QuizBuilder(QUIZ_ONE).withQuizId("editedQuizID").build();
        assertFalse(editedQuiz1.equals(QUIZ_ONE));
    }
}
