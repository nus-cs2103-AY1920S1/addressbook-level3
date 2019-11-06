package seedu.address.logic.commands.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.GuiTheme;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.TypicalModulesInfo;

class ChangeThemeCommandTest {

    @Test
    public void constructor_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ChangeThemeCommand(null));
    }

    @Test
    public void execute_changeToDark_successful()
            throws CommandException {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(GuiTheme.DARK);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(600, 600,
                0, 0, GuiTheme.LIGHT));
        Model model = new ModelManager(new ModulePlannerBuilder().build(),
                userPrefs, TypicalModulesInfo.getTypicalModulesInfo());

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(new GuiSettings(600, 600,
                0, 0, GuiTheme.DARK));
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().build(),
                expectedUserPrefs, TypicalModulesInfo.getTypicalModulesInfo());

        // construct command to collapse semester
        CommandResult res = changeThemeCommand.execute(model);
        assertEquals(expectedModel.getGuiSettings().getGuiTheme(),
                model.getGuiSettings().getGuiTheme());
        assertEquals(res.getFeedbackToUser(),
                String.format(ChangeThemeCommand.MESSAGE_SUCCESS, GuiTheme.DARK.getModeName()));
    }

    @Test
    void testEquals() {
        ChangeThemeCommand changeThemeCommand =
                new ChangeThemeCommand(GuiTheme.LIGHT);
        ChangeThemeCommand otherChangeThemeCommand =
                new ChangeThemeCommand(GuiTheme.DARK);

        // same object -> returns true
        assertEquals(changeThemeCommand, changeThemeCommand);

        // same values -> returns true
        ChangeThemeCommand changeThemeCommandCopy =
                new ChangeThemeCommand(GuiTheme.LIGHT);
        assertEquals(changeThemeCommand, changeThemeCommandCopy);

        // different types -> returns false
        assertNotEquals(1, changeThemeCommand);

        // null -> returns false
        assertNotEquals(null, changeThemeCommand);

        // different sem -> returns false
        assertNotEquals(changeThemeCommand, otherChangeThemeCommand);
    }
}
