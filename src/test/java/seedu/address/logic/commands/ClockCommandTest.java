package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ClockCommand.SHOWING_CLOCK_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserSettings;
import seedu.address.model.settings.ClockFormat;

class ClockCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void constructor_nullClockFormat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClockCommand(null));
    }

    @Test
    void execute_clockCommand_success() {
        // ensure default is TWENTY_FOUR
        CommandResult expectedCommandResult = new CommandResult(SHOWING_CLOCK_MESSAGE
                + ClockFormat.TWENTY_FOUR.getDisplayName());
        assertCommandSuccess(new ClockCommand(ClockFormat.TWENTY_FOUR), model, expectedCommandResult, expectedModel);
    }

    @Test
    void execute_validClockFormat_changesDefaultFormatSuccess() {
        // since default is TWENTY_FOUR
        expectedModel.setClockFormat(ClockFormat.TWELVE);
        CommandResult expectedCommandResult = new CommandResult(SHOWING_CLOCK_MESSAGE
                + ClockFormat.TWELVE.getDisplayName());
        assertCommandSuccess(new ClockCommand(ClockFormat.TWELVE), model, expectedCommandResult, expectedModel);
        assertEquals(ClockFormat.TWELVE, model.getCurrentClockFormat());
        // ensure default display format of +Work is changed
        assertEquals(DateTimeUtil.getDisplayFormatterTwelveHour(), DateTimeUtil.getDefaultDisplayFormat());
    }

}
