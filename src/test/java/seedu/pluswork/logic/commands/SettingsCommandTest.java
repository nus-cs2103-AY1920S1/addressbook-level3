package seedu.pluswork.logic.commands;

import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.logic.commands.settings.SettingsCommand.SHOWING_SETTINGS_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.settings.SettingsCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;

class SettingsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void execute_settings_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_SETTINGS_MESSAGE);
        assertCommandSuccess(new SettingsCommand(), model, expectedCommandResult, expectedModel);
    }
}
