package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.quiz.QuizCommand.BLANK_QUIZ_ID;
import static seedu.address.logic.commands.quiz.QuizCommand.QUIZ_ALREADY_EXISTS;

import java.util.ArrayList;
import java.util.HashMap;

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


public class QuizCreateManuallyCommandTest {

    /**
     * Checks if two QuizCreateManuallyCommands are equal.
     */
    @Test
    public void equals() {
        String quizId = QuizBuilder.DEFAULT_QUIZ_ID;
        String otherQuizId = "Other Quiz";
        HashMap<String, String> fields = new HashMap<>();
        fields.put("quizID", quizId);
        fields.put("questionNumbers", "1");
        HashMap<String, String> otherFields = new HashMap<>();
        otherFields.put("quizID", otherQuizId);
        otherFields.put("questionNumbers", "1");
        QuizCreateManuallyCommand createManuallyCommand =
                new QuizCreateManuallyCommand(fields);
        QuizCreateManuallyCommand otherCreateManuallyCommand =
                new QuizCreateManuallyCommand(otherFields);
        // same object -> returns true
        assertTrue(createManuallyCommand.equals(createManuallyCommand));

        // same values -> returns true
        QuizCreateManuallyCommand createManuallyCommandCopy =
                new QuizCreateManuallyCommand(fields);
        assertTrue(createManuallyCommand.equals(createManuallyCommandCopy));

        // different types -> returns false
        assertFalse(createManuallyCommand.equals(1));

        // null -> returns false
        assertFalse(createManuallyCommand.equals(null));

        // different quiz -> returns false
        assertFalse(createManuallyCommand.equals(otherCreateManuallyCommand));
    }

    /**
     * Test for creating a quiz with one question successfully.
     */
    @Test
    public void execute_createQuiz_success() throws Exception {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("quizID", "createQuizManual");
        fields.put("questionNumbers", "1");
        QuizCreateManuallyCommand quizCreateManuallyCommand =
                new QuizCreateManuallyCommand(fields);
        Question question = new QuestionBuilder().build();
        ModelStub modelStub =
                new QuizCreateManuallyCommandTest.ModelStubWithQuizWithQuestion("CreateCopy", question);
        CommandResult commandResult = quizCreateManuallyCommand.execute(modelStub);
        String expectedMessage = "Created quiz: " + "createQuizManual" + " with " + 1 + " question.";
        assertEquals(expectedMessage,
                commandResult.getFeedbackToUser());
    }

    /**
     * Test for creating a quiz with two questions successfully.
     */
    @Test
    public void execute_createQuizMulti_success() throws Exception {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("quizID", "createQuizMulti");
        fields.put("questionNumbers", "1 2");
        QuizCreateManuallyCommand quizCreateManuallyCommand =
                new QuizCreateManuallyCommand(fields);
        Question question = new QuestionBuilder().build();
        Question otherQuestion = new QuestionBuilder().withQuestion("Test?").build();
        ModelStub modelStub =
                new QuizCreateManuallyCommandTest.ModelStubWithQuizWithQuestion("CreateCopy", question);
        modelStub.addQuestion(otherQuestion);
        CommandResult commandResult = quizCreateManuallyCommand.execute(modelStub);
        String expectedMessage = "Created quiz: " + "createQuizMulti" + " with " + 2 + " questions.";
        assertEquals(expectedMessage,
                commandResult.getFeedbackToUser());
    }

    /**
     * Test for creating quiz unsuccessfully, due to duplicate quiz ID.
     */
    @Test
    public void execute_createDuplicateQuiz_throwsCommandException() {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("quizID", "quiz3");
        fields.put("questionNumbers", "1 2");
        QuizCreateManuallyCommand quizCreateManuallyCommand =
                new QuizCreateManuallyCommand(fields);
        Question question = new QuestionBuilder().build();
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("quiz", question);
        assertThrows(CommandException.class, () -> quizCreateManuallyCommand.execute(modelStub),
                String.format(QUIZ_ALREADY_EXISTS, "quiz3"));
    }

    /**
     * Test for creating quiz unsuccessfully, due to quiz ID not present.
     */
    @Test
    public void execute_createQuizWithMissingQuizId_throwsCommandException() {
        HashMap<String, String> fields = new HashMap<>();
        fields.put("quizID", "");
        fields.put("questionNumbers", "1 2");
        QuizCreateManuallyCommand quizCreateManuallyCommand =
                new QuizCreateManuallyCommand(fields);
        Question question = new QuestionBuilder().build();
        ModelStub modelStub = new ModelStubWithQuizWithQuestion("quizzy", question);
        assertThrows(CommandException.class, () -> quizCreateManuallyCommand.execute(modelStub),
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
        public void addQuestion(Question question) {
            savedQuestions.addQuestion(question);
        }

        @Override
        public boolean checkQuizExists(String quizId) {
            return savedQuizzes.checkQuizExists(quizId);
        }

        @Override
        public boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber) {
            return savedQuizzes.addQuizQuestion(quizId, questionNumber, quizQuestionNumber, savedQuestions);
        }

        @Override
        public boolean createQuizManually(String quizId, ArrayList<Integer> questionNumbers) {
            return savedQuizzes.createQuizManually(quizId, questionNumbers, savedQuestions);
        }

    }
}
