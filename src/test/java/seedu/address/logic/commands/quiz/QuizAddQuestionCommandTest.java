package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.quiz.QuizCommand.BLANK_QUIZ_ID;
import static seedu.address.logic.commands.quiz.QuizCommand.INVALID_QUESTION_NUMBERS;
import static seedu.address.logic.commands.quiz.QuizCommand.REPEATED_QUESTION;

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
 * Test for QuizAddQuestionCommand.
 */
public class QuizAddQuestionCommandTest {

    /**
     * Tests whether two QuizAddQuestionCommands are equal.
     */
    @Test
    public void equals() {
        String quizId = QuizBuilder.DEFAULT_QUIZ_ID;
        String otherQuizId = "Other Quiz";
        QuizAddQuestionCommand addQuestionCommand =
                new QuizAddQuestionCommand(quizId, 1, 1);
        QuizAddQuestionCommand otherAddQuestionCommand =
                new QuizAddQuestionCommand(otherQuizId, 1, 1);
        // same object -> returns true
        assertTrue(addQuestionCommand.equals(addQuestionCommand));

        // same values -> returns true
        QuizAddQuestionCommand addQuestionCommandCopy =
                new QuizAddQuestionCommand(quizId, 1, 1);
        assertTrue(addQuestionCommand.equals(addQuestionCommandCopy));

        // different types -> returns false
        assertFalse(addQuestionCommand.equals(1));

        // null -> returns false
        assertFalse(addQuestionCommand.equals(null));

        // different quiz -> returns false
        assertFalse(addQuestionCommand.equals(otherAddQuestionCommand));
    }

    /**
     * Test for adding to quiz successfully.
     */
    @Test
    public void execute_addUniqueQuestionToQuiz_success() throws Exception {
        QuizAddQuestionCommand quizAddQuestionCommand =
                new QuizAddQuestionCommand("Success", 4, 1);
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("Success", 3, 1);
        CommandResult commandResult = quizAddQuestionCommand.execute(modelStub);
        String expectedMessage = "Added question: " + 4 + " to quiz: " + "Success" + ".";
        assertEquals(expectedMessage,
                commandResult.getFeedbackToUser());
    }

    /**
     * Test for adding to quiz unsuccessfully, due to duplicate question present in group.
     */
    @Test
    public void execute_duplicateQuestionToQuiz_throwsCommandException() {
        QuizAddQuestionCommand quizAddQuestionCommand =
                new QuizAddQuestionCommand("quizID", 4, 1);
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("quizID", 4, 1);
        assertThrows(CommandException.class, () -> quizAddQuestionCommand.execute(modelStub),
                String.format(REPEATED_QUESTION));
    }

    /**
     * Test for adding to quiz unsuccessfully, due to question number out of bounds.
     */
    @Test
    public void execute_addQuestionNumberOutOfBounds_throwsCommandException() {
        QuizAddQuestionCommand quizAddQuestionCommand =
                new QuizAddQuestionCommand("quizID", -1, 1);
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("quizID", 1, 1);
        assertThrows(CommandException.class, () -> quizAddQuestionCommand.execute(modelStub),
                INVALID_QUESTION_NUMBERS);
    }

    /**
     * Test for adding to quiz unsuccessfully, due to quiz question number out of bounds.
     */
    @Test
    public void execute_addQuizQuestionNumberOutOfBounds_throwsCommandException() {
        QuizAddQuestionCommand quizAddQuestionCommand =
                new QuizAddQuestionCommand("quizID", 1, -1);
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("quizID", 1, 1);
        assertThrows(CommandException.class, () -> quizAddQuestionCommand.execute(modelStub),
                INVALID_QUESTION_NUMBERS);
    }

    /**
     * Test for adding to quiz unsuccessfully, due to quiz ID not present.
     */
    @Test
    public void execute_addQuestionToQuizWithMissingQuizId_throwsCommandException() {
        QuizAddQuestionCommand quizAddQuestionCommand =
                new QuizAddQuestionCommand("", 1, 1);
        Question question = new QuestionBuilder().withQuestion("").withAnswer("").build();
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("NormalThree", question);
        assertThrows(CommandException.class, () -> quizAddQuestionCommand.execute(modelStub),
                BLANK_QUIZ_ID);
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
        public boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber) {
            return savedQuizzes.addQuizQuestion(quizId, questionNumber, quizQuestionNumber, savedQuestions);
        }

    }
}
