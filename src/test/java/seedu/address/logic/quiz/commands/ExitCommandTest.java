package seedu.address.logic.quiz.commands;

import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.quiz.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.ModelQuizManager;


public class ExitCommandTest {
    private Model model = new ModelQuizManager();
    private Model expectedModel = new ModelQuizManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
