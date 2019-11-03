package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.Quiz;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.quiz.QuizBuilder;

public class QuizAddQuestionCommandTest {

    @Test
    public void constructor_nullQuiz_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new QuizAddQuestionCommand(null,
                QuizBuilder.DEFAULT_QUESTION_INDEX, QuizBuilder.DEFAULT_QUIZ_QUESTION_INDEX));
    }

    @Test
    public void equals() {
        String quizId = QuizBuilder.DEFAULT_QUIZ_ID;
        String otherQuizId = "Other Quiz";
        QuizAddQuestionCommand addQuestionCommand = new QuizAddQuestionCommand(quizId,
                QuizBuilder.DEFAULT_QUESTION_INDEX, QuizBuilder.DEFAULT_QUIZ_QUESTION_INDEX);
        QuizAddQuestionCommand otherAddQuestionCommand = new QuizAddQuestionCommand(otherQuizId,
                QuizBuilder.DEFAULT_QUESTION_INDEX, QuizBuilder.DEFAULT_QUIZ_QUESTION_INDEX);

        // same object -> returns true
        assertTrue(addQuestionCommand.equals(addQuestionCommand));

        // same values -> returns true
        QuizAddQuestionCommand addQuestionCommandCopy = new QuizAddQuestionCommand(quizId,
                QuizBuilder.DEFAULT_QUESTION_INDEX, QuizBuilder.DEFAULT_QUIZ_QUESTION_INDEX);
        assertTrue(addQuestionCommand.equals(addQuestionCommandCopy));

        // different types -> returns false
        assertFalse(addQuestionCommand.equals(1));

        // null -> returns false
        assertFalse(addQuestionCommand.equals(null));

        // different quiz -> returns false
        assertFalse(addQuestionCommand.equals(otherAddQuestionCommand));
    }

    /**
     * A Model stub that contains a single quiz.
     */
    private class ModelStubWithQuiz extends ModelStub {
        private final String quizId;
        private final Quiz quiz;

        ModelStubWithQuiz(String quizId) {
            requireNonNull(quizId);
            Quiz quiz = new QuizBuilder().withQuizId(quizId).build();
            this.quizId = quizId;
            this.quiz = quiz;
        }

        @Override
        public boolean checkQuizExists(String quizId) {
            requireNonNull(quizId);
            return this.quizId.equals(quizId);
        }
    }

    /**
     * A Model stub that always accept the quiz being added.
     */
    private class ModelStubAcceptingQuizAdded extends ModelStub {
        final ArrayList<Quiz> quizzesAdded = new ArrayList<>();

        @Override
        public boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber) {
            requireNonNull(quizId);
            Quiz quiz = new QuizBuilder().withQuizId(quizId).build();
            return quizzesAdded.add(quiz);
        }

    }

}
