package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.quiz.QuizCommand.BLANK_QUIZ_ID;

import java.io.IOException;

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
 * Test for QuizExportCommand.
 */
public class QuizExportCommandTest {

    /**
     * Tests if two QuizExportCommand are equal.
     */
    @Test
    public void equals() {
        String quizId = QuizBuilder.DEFAULT_QUIZ_ID;
        String otherQuizId = "Other Quiz";
        QuizExportCommand exportCommand = new QuizExportCommand(quizId);
        QuizExportCommand otherExportCommand = new QuizExportCommand(otherQuizId);
        // same object -> returns true
        assertTrue(exportCommand.equals(exportCommand));

        // same values -> returns true
        QuizExportCommand exportCommandCopy = new QuizExportCommand(quizId);
        assertTrue(exportCommand.equals(exportCommandCopy));

        // different types -> returns false
        assertFalse(exportCommand.equals(1));

        // null -> returns false
        assertFalse(exportCommand.equals(null));

        assertFalse(exportCommand.equals(otherExportCommand));
    }

    /**
     * Test for exporting a quiz successfully.
     */
    @Test
    public void execute_exportQuiz_success() throws Exception {
        QuizExportCommand exportCommand = new QuizExportCommand("Export");
        Question question = new QuestionBuilder().build();
        ModelStub modelStub = new QuizExportCommandTest.ModelStubWithQuizWithQuestion("Export", question);
        CommandResult commandResult = exportCommand.execute(modelStub);
        String expectedMessage = "Successfully exported to "
                + "Export" + ".html in your current directory.";
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    /**
     * Test for exporting a quiz unsuccessfully, as quiz id is not present.
     */
    @Test
    public void execute_exportQuizInvalidQuizId_throwsCommandException() throws Exception {
        QuizExportCommand exportCommand = new QuizExportCommand("");
        Question question = new QuestionBuilder().build();
        ModelStub modelStub = new QuizExportCommandTest.ModelStubWithQuizWithQuestion("GetTwo", question);
        assertThrows(CommandException.class, () -> exportCommand.execute(modelStub),
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
        public boolean exportQuiz(String quizId) throws IOException {
            return true;
        }
    }
}
