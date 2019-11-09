package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.UserSettings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ViewSettingsCommandTest {
    @Test
    public void execute_printsUserSettings_successful() throws Exception {
        Model modelManager = new ModelManager();

        UserSettings currentUserSettings = modelManager.getUserSettings();

        ViewSettingsCommand viewSettingsCommand = new ViewSettingsCommand();
        CommandResult commandResult = viewSettingsCommand.execute(modelManager);

        String expectedMsg = String.format(ViewSettingsCommand.MESSAGE_SET_USER_SETTINGS_SUCCESS, currentUserSettings);

        assertEquals(commandResult.getFeedbackToUser(), expectedMsg);
    }

    @Test
    public void equal() {
        ViewSettingsCommand standardCommand = new ViewSettingsCommand();

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different object type -> returns false
        assertFalse(standardCommand.equals(new DoneCommand()));

        // null -> returns false
        assertFalse(standardCommand.equals(null));
    }
}
