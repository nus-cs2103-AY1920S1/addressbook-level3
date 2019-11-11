package seedu.pluswork.logic.commands.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.logic.commands.settings.ClockCommand.SHOWING_CLOCK_MESSAGE;
import static seedu.pluswork.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.settings.ClockFormat;

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
