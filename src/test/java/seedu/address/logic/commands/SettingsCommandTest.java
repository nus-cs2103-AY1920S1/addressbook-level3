package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SettingsCommand.SHOWING_SETTINGS_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class SettingsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void execute_settings_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_SETTINGS_MESSAGE);
        assertCommandSuccess(new SettingsCommand(), model, expectedCommandResult, expectedModel);
    }
}