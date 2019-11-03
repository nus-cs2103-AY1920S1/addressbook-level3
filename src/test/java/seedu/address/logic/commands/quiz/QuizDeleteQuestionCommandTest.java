package seedu.address.logic.commands.quiz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.quiz.TypicalSavedQuizzes.getTypicalSavedQuizzes;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.quiz.QuizBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * {@code QuizDeleteQuestionCommand}.
 */
public class QuizDeleteQuestionCommandTest {

    private Model model = new ModelManager();

    public QuizDeleteQuestionCommandTest() {
        model.setSavedQuizzes(getTypicalSavedQuizzes());
    }

    @Test
    public void equals() {
        QuizDeleteQuestionCommand deleteFirstCommand =
                new QuizDeleteQuestionCommand(QuizBuilder.DEFAULT_QUIZ_ID,
                        QuizBuilder.DEFAULT_QUIZ_QUESTION_INDEX);
        QuizDeleteQuestionCommand deleteSecondCommand =
                new QuizDeleteQuestionCommand("Quiz2",
                        QuizBuilder.DEFAULT_QUIZ_QUESTION_INDEX);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        QuizDeleteQuestionCommand deleteFirstCommandCopy =
                new QuizDeleteQuestionCommand(QuizBuilder.DEFAULT_QUIZ_ID,
                QuizBuilder.DEFAULT_QUIZ_QUESTION_INDEX);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different quiz -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
