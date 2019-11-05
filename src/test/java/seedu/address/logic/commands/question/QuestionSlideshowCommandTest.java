package seedu.address.logic.commands.question;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class QuestionSlideshowCommandTest {

    private Model model = new ModelManager();

    public QuestionSlideshowCommandTest() {
        model.setSavedQuestions(getTypicalSavedQuestions());
    }

    @Test
    public void execute_indexLessThanOrEqualToZero_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
            new QuestionSlideshowCommand("0").execute(model));
        assertThrows(IndexOutOfBoundsException.class, () ->
            new QuestionSlideshowCommand("-1").execute(model));
    }

    @Test
    public void execute_validInputWithoutSpace_startSlideshow() {
        Model expectedModel = new ModelManager();
        expectedModel.setSavedQuestions(getTypicalSavedQuestions());

        assertCommandSuccess(
            new QuestionSlideshowCommand("1"),
            model,
            new CommandResult(QuestionSlideshowCommand.MESSAGE_SUCCESS,
                CommandResultType.SHOW_SLIDESHOW),
            expectedModel);

        assertCommandSuccess(
            new QuestionSlideshowCommand("2"),
            model,
            new CommandResult(QuestionSlideshowCommand.MESSAGE_SUCCESS,
                CommandResultType.SHOW_SLIDESHOW),
            expectedModel);
    }

    @Test
    public void execute_validInputWithSpace_startSlideshow() {
        Model expectedModel = new ModelManager();
        expectedModel.setSavedQuestions(getTypicalSavedQuestions());

        assertCommandSuccess(
            new QuestionSlideshowCommand("    1   2"),
            model,
            new CommandResult(QuestionSlideshowCommand.MESSAGE_SUCCESS,
                CommandResultType.SHOW_SLIDESHOW),
            expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        QuestionSlideshowCommand invalidIndexSlideshowCmd = new QuestionSlideshowCommand("1 2 3");

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX, ()
            -> invalidIndexSlideshowCmd.execute(model));
    }

}
