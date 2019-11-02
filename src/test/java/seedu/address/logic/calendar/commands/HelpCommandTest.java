package seedu.address.logic.calendar.commands;

import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.calendar.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;


public class HelpCommandTest {
    private CalendarModel calendarModel = new CalendarModelManager();
    private CalendarModel expectedModel = new CalendarModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), calendarModel, expectedCommandResult, expectedModel);
    }
}
