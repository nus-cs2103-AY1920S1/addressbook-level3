package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ToggleUiCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class ToggleUiCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void execute_toggleUi_success() {
        expectedModel.toggleGuiSettingsTheme();
        CommandResult expectedCommandResult = CommandResult.commandResultToggleUi(MESSAGE_SUCCESS);
        assertCommandSuccess(new ToggleUiCommand(), model, expectedCommandResult, expectedModel);
    }
}
