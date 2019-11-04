package seedu.address.logic.commands.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.question.Question;

public class QuestionDeleteCommandTest {

    private Model model = new ModelManager();

    public QuestionDeleteCommandTest() {
        model.setSavedQuestions(getTypicalSavedQuestions());
    }

    @Test
    public void execute_deleteQuestion_success() {
        Index index = Index.fromOneBased(1);
        QuestionDeleteCommand deleteCommand = new QuestionDeleteCommand(index);

        Question expectedQuestion = model.getQuestion(index);
        String expectedMessage = String
            .format(QuestionDeleteCommand.MESSAGE_SUCCESS, expectedQuestion);
        assertCommandSuccess(deleteCommand, model,
            new CommandResult(expectedMessage, CommandResultType.SHOW_QUESTION), model);
    }

    @Test
    public void execute_invalidQuestionIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getAllQuestions().size() + 1);
        QuestionDeleteCommand deleteCommand = new QuestionDeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX, ()
            -> deleteCommand.execute(model));
    }

    @Test
    public void equals() {
        QuestionDeleteCommand deleteCommand = new QuestionDeleteCommand(Index.fromOneBased(1));

        // Same object
        assertTrue(deleteCommand.equals(deleteCommand));

        // Null
        assertFalse(deleteCommand.equals(null));

        // Different index
        QuestionDeleteCommand deleteCommandDiffIndex = new QuestionDeleteCommand(
            Index.fromOneBased(2));
        assertFalse(deleteCommand.equals(deleteCommandDiffIndex));
    }

}
