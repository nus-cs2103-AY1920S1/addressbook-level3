package seedu.address.logic.commands.quiz;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.quiz.TypicalSavedQuizzes.getTypicalSavedQuizzes;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.quiz.QuizBuilder;

public class QuizShowQuestionsCommandTest {

    private Model model = new ModelManager();

    public QuizShowQuestionsCommandTest() {
        model.setSavedQuizzes(getTypicalSavedQuizzes());
    }

    @Test
    public void execute_validList_showsEverything() {
        Model expectedModel = new ModelManager();
        expectedModel.setSavedQuizzes(getTypicalSavedQuizzes());

        assertCommandSuccess(
                new QuizShowQuestionsCommand(QuizBuilder.DEFAULT_QUIZ_ID),
                model,
                new CommandResult("Showing questions for " + QuizBuilder.DEFAULT_QUIZ_ID + ".",
                        CommandResultType.SHOW_QUIZ_QUESTIONS),
                expectedModel);
    }
}
