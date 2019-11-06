package seedu.address.logic.commands.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiMode;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.TypicalModulesInfo;

class ChangeModeCommandTest {

    @Test
    public void constructor_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ChangeModeCommand(null));
    }

    @Test
    public void execute_changeToDark_successful()
            throws CommandException {
        ChangeModeCommand changeModeCommand = new ChangeModeCommand(GuiMode.DARK);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(600, 600,
                0, 0, GuiMode.LIGHT));
        Model model = new ModelManager(new ModulePlannerBuilder().build(),
                userPrefs, TypicalModulesInfo.getTypicalModulesInfo());

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(new GuiSettings(600, 600,
                0, 0, GuiMode.DARK));
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().build(),
                expectedUserPrefs, TypicalModulesInfo.getTypicalModulesInfo());

        // construct command to collapse semester
        CommandResult res = changeModeCommand.execute(model);
        assertEquals(expectedModel.getGuiSettings().getMode(),
                model.getGuiSettings().getMode());
        assertEquals(res.getFeedbackToUser(),
                String.format(ChangeModeCommand.MESSAGE_SUCCESS, GuiMode.DARK.getModeName()));
    }

    @Test
    void testEquals() {
        ChangeModeCommand changeModeCommand =
                new ChangeModeCommand(GuiMode.LIGHT);
        ChangeModeCommand otherChangeModeCommand =
                new ChangeModeCommand(GuiMode.DARK);

        // same object -> returns true
        assertEquals(changeModeCommand, changeModeCommand);

        // same values -> returns true
        ChangeModeCommand changeModeCommandCopy =
                new ChangeModeCommand(GuiMode.LIGHT);
        assertEquals(changeModeCommand, changeModeCommandCopy);

        // different types -> returns false
        assertNotEquals(1, changeModeCommand);

        // null -> returns false
        assertNotEquals(null, changeModeCommand);

        // different sem -> returns false
        assertNotEquals(changeModeCommand, otherChangeModeCommand);
    }
}
