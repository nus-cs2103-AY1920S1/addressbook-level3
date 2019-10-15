package seedu.address.logic.commands;

import static seedu.address.logic.commands.AchvmCommand.SHOWING_ACHVM_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class AchvmCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_achvm_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_ACHVM_MESSAGE, false, false,
                true, false);
        assertCommandSuccess(new AchvmCommand(), model, expectedCommandResult, expectedModel);
    }
}
