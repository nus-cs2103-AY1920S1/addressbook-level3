package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.quiz.QuizCommand.BLANK_QUIZ_ID;
import static seedu.address.logic.commands.quiz.QuizCommand.INVALID_QUESTION_INDEX;
import static seedu.address.logic.commands.quiz.QuizCommand.INVALID_QUESTION_NUMBERS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.question.Question;
import seedu.address.model.question.SavedQuestions;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.question.QuestionBuilder;
import seedu.address.testutil.question.TypicalQuestions;
import seedu.address.testutil.quiz.QuizBuilder;

/**
 * Test for QuizDeleteQuestionCommand.
 */
public class QuizDeleteQuestionCommandTest {

    /**
     * Tests if two QuizDeleteQuestionCommands are equal.
     */
    @Test
    public void equals() {
        String quizId = QuizBuilder.DEFAULT_QUIZ_ID;
        String otherQuizId = "Other Quiz";
        QuizDeleteQuestionCommand deleteQuestionCommand =
                new QuizDeleteQuestionCommand(quizId, 1);
        QuizDeleteQuestionCommand otherDeleteQuestionCommand =
                new QuizDeleteQuestionCommand(otherQuizId, 1);
        // same object -> returns true
        assertTrue(deleteQuestionCommand.equals(deleteQuestionCommand));

        // same values -> returns true
        QuizDeleteQuestionCommand deleteQuestionCommandCopy = new QuizDeleteQuestionCommand(quizId, 1);
        assertTrue(deleteQuestionCommand.equals(deleteQuestionCommandCopy));

        // different types -> returns false
        assertFalse(deleteQuestionCommand.equals(1));

        // null -> returns false
        assertFalse(deleteQuestionCommand.equals(null));

        // different quiz -> returns false
        assertFalse(deleteQuestionCommand.equals(otherDeleteQuestionCommand));
    }

    /**
     * Test for deleting a question from a quiz successfully.
     */
    @Test
    public void execute_deleteExistingQuestionFromQuiz_success() throws Exception {
        QuizDeleteQuestionCommand quizDeleteQuestionCommand =
                new QuizDeleteQuestionCommand("Remove", 1);
        Question question = new QuestionBuilder().withQuestion("RemoveTest?").withAnswer("Remove").build();
        ModelStub modelStub =
                new QuizDeleteQuestionCommandTest.ModelStubWithQuizWithQuestion("Remove", question);
        CommandResult commandResult = quizDeleteQuestionCommand.execute(modelStub);
        String expectedMessage = "Removed question: " + 1 + " from quiz: " + "Remove";
        assertEquals(expectedMessage,
                commandResult.getFeedbackToUser());
    }

    /**
     * Test for deleting from quiz unsuccessfully, due to quiz ID not present.
     */
    @Test
    public void execute_deleteQuestionFromQuizWithMissingQuizId_throwsCommandException() {
        QuizDeleteQuestionCommand quizDeleteQuestionCommand =
                new QuizDeleteQuestionCommand("", 1);
        Question question = new QuestionBuilder().withQuestion("").withAnswer("").build();
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("NormalThree", question);
        assertThrows(CommandException.class, () -> quizDeleteQuestionCommand.execute(modelStub),
                BLANK_QUIZ_ID);
    }

    /**
     * Test for removing a question unsuccessfully because question index number is out of bounds.
     */
    @Test
    public void execute_removeQuestionIndexNumberOutOfBounds_throwsCommandException() {
        QuizDeleteQuestionCommand quizDeleteQuestionCommand =
                new QuizDeleteQuestionCommand("RemoveFail", -1);
        Question question = new QuestionBuilder().build();
        ModelStub modelStub =
                new QuizDeleteQuestionCommandTest.ModelStubWithQuizWithQuestion("RemoveFail", question);
        assertThrows(CommandException.class, () -> quizDeleteQuestionCommand.execute(modelStub),
                INVALID_QUESTION_NUMBERS);
    }

    /**
     * Test for removing a question unsuccessfully because question index number is invalid.
     */
    @Test
    public void execute_removeQuestionIndexNumberInvalid_throwsCommandException() {
        QuizDeleteQuestionCommand quizDeleteQuestionCommand =
                new QuizDeleteQuestionCommand("RemoveFail", 100);
        Question question = new QuestionBuilder().build();
        ModelStub modelStub =
                new QuizDeleteQuestionCommandTest.ModelStubWithQuizWithQuestion("RemoveFail", question);
        assertThrows(CommandException.class, () -> quizDeleteQuestionCommand.execute(modelStub),
                INVALID_QUESTION_INDEX);
    }

    /**
     * A Model stub that contains a single quiz.
     */
    private class ModelStubWithQuizWithQuestion extends ModelStub {
        private final SavedQuizzes savedQuizzes;
        private final SavedQuestions savedQuestions;

        /**
         * Creates a ModelStubWithQuizWithQuestion instance.
         *
         * @param quizId Quiz ID of the quiz.
         * @param questionNumber Question number of the question.
         * @param quizIndexNumber Quiz Index Number to add to.
         */
        ModelStubWithQuizWithQuestion(String quizId, int questionNumber, int quizIndexNumber) {
            requireNonNull(quizId);
            Quiz quiz = new QuizBuilder().withQuizId(quizId).build();
            this.savedQuizzes = new SavedQuizzes();
            this.savedQuestions = new SavedQuestions(TypicalQuestions.getTypicalSavedQuestionsForQuiz());
            Question question = savedQuestions.getQuestion(Index.fromOneBased(questionNumber));
            quiz.addQuestion(quizIndexNumber, question);
            savedQuizzes.addQuiz(quiz);
        }

        /**
         * Creates a ModelStubWithQuizWithQuestion instance.
         *
         * @param quizId Quiz Id of the quiz.
         * @param question Question in the quiz.
         */
        ModelStubWithQuizWithQuestion(String quizId, Question question) {
            requireNonNull(quizId);
            Quiz quiz = new QuizBuilder().withQuizId(quizId).build();
            this.savedQuizzes = new SavedQuizzes();
            this.savedQuestions = new SavedQuestions();
            savedQuestions.addQuestion(question);
            quiz.addQuestion(question);
            savedQuizzes.addQuiz(quiz);
        }

        @Override
        public boolean checkQuizExists(String quizId) {
            return true;
        }

        @Override
        public boolean deleteQuizQuestion(String quizId, int questionNumber) {
            return savedQuizzes.deleteQuizQuestion(quizId, questionNumber);
        }
    }

}
