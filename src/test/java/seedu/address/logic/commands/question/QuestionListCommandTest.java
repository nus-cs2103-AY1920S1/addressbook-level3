package seedu.address.logic.commands.question;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class QuestionListCommandTest {
    private Model model = new ModelManager();

    public QuestionListCommandTest() {
        model.setSavedQuestions(getTypicalSavedQuestions());
    }

    @Test
    public void execute_validList_showsEverything() {
        Model expectedModel = new ModelManager();
        expectedModel.setSavedQuestions(getTypicalSavedQuestions());

        assertCommandSuccess(
            new QuestionListCommand(),
            model,
            new CommandResult(model.getQuestionsSummary(), CommandResultType.SHOW_QUESTION),
            expectedModel);
    }
}
