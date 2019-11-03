package seedu.address.logic.commands.question;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class QuestionFindCommandTest {

    private Model model = new ModelManager();

    public QuestionFindCommandTest() {
        model.setSavedQuestions(getTypicalSavedQuestions());
    }

    @Test
    public void execute_validSearch_showsSearchedQuestions() {
        Model expectedModel = new ModelManager();
        expectedModel.setSavedQuestions(getTypicalSavedQuestions());

        assertCommandSuccess(
            new QuestionFindCommand("What"),
            model,
            new CommandResult(model.searchQuestions("What"),
                CommandResultType.SHOW_QUESTION_SEARCH),
            expectedModel);
    }
}
