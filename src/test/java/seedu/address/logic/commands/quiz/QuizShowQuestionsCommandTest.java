package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.quiz.QuizCommand.BLANK_QUIZ_ID;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
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
 * Tests QuizShowQuestionsCommand.
 */
public class QuizShowQuestionsCommandTest {

    /**
     * Tests if two QuizShowQuestionsCommands are equal.
     */
    @Test
    public void equals() {
        String quizId = QuizBuilder.DEFAULT_QUIZ_ID;
        String otherQuizId = "Other Group";
        QuizShowQuestionsCommand getQuestionsCommand = new QuizShowQuestionsCommand(quizId);
        QuizShowQuestionsCommand otherGetQuestionsCommand = new QuizShowQuestionsCommand(otherQuizId);
        // same object -> returns true
        assertTrue(getQuestionsCommand.equals(getQuestionsCommand));

        // same values -> returns true
        QuizShowQuestionsCommand getQuestionsCommandCopy = new QuizShowQuestionsCommand(quizId);
        assertTrue(getQuestionsCommand.equals(getQuestionsCommandCopy));

        // different types -> returns false
        assertFalse(getQuestionsCommand.equals(1));

        // null -> returns false
        assertFalse(getQuestionsCommand.equals(null));

        // different quiz -> returns false
        assertFalse(getQuestionsCommand.equals(otherGetQuestionsCommand));
    }

    /**
     * Test for getting a quiz successfully.
     */
    @Test
    public void execute_getQuiz_success() throws Exception {
        QuizShowQuestionsCommand quizShowQuestionsCommand = new QuizShowQuestionsCommand("Get");
        Question question = new QuestionBuilder().build();
        ModelStub modelStub = new QuizShowQuestionsCommandTest.ModelStubWithQuizWithQuestion("Get", question);
        CommandResult commandResult = quizShowQuestionsCommand.execute(modelStub);
        assertEquals("Showing questions for " + "Get" + ".", commandResult.getFeedbackToUser());
    }

    /**
     * Test for getting a quiz unsuccessfully, as quiz ID is empty.
     */
    @Test
    public void execute_getQuizEmptyQuizId_throwsCommandException() throws Exception {
        QuizShowQuestionsCommand quizShowQuestionsCommand = new QuizShowQuestionsCommand("");
        Question question = new QuestionBuilder().build();
        ModelStub modelStub =
                new QuizShowQuestionsCommandTest.ModelStubWithQuizWithQuestion("GetThree", question);
        assertThrows(CommandException.class, () -> quizShowQuestionsCommand.execute(modelStub), BLANK_QUIZ_ID);
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
        public ObservableList<Question> getObservableListQuestionsFromQuiz() {
            return savedQuizzes.getObservableListQuestionsFromQuiz();
        }
    }
}
