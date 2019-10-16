package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.Month;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.logic.commands.CommandResult;

/**
 * Shows user the his/her calendar for the specified month.
 */
public class ShowCommand extends Command {
    public static final String COMMAND_WORD = "show";
    public static final String SHOWING_REQUESTED_MESSAGE = "Showing month view for %s %d";

    MonthOfYear monthOfYear;
    int year;

    public ShowCommand(MonthOfYear monthOfYear, int year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
    }

    public CommandResult execute(Calendar calendar) {
        Month requestedMonth = new Month(monthOfYear, year);
        calendar.updateMonthShown(requestedMonth);
        return new CommandResult(SHOWING_REQUESTED_MESSAGE);
    }
}
