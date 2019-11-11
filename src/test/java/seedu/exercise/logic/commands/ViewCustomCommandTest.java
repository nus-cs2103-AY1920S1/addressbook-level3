package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.ViewCustomCommand.SHOWING_VIEW_CUSTOM_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class ViewCustomCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_VIEW_CUSTOM_MESSAGE, false, false, false, true);
        assertCommandSuccess(new ViewCustomCommand(), model, expectedCommandResult, expectedModel);
    }
}
