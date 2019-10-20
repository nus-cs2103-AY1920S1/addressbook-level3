package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.Month;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.calendar.model.Year;
import seedu.address.logic.commands.CommandResult;

/**
 * Shows user the his/her calendar for the specified month.
 */
public class ShowCommand extends Command {
    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SHOWING_REQUESTED = "Showing month view for %s %s";
    public static final String MESSAGE_USAGE = ""; //todo add usage message

    MonthOfYear monthOfYear;
    Year year;

    public ShowCommand(MonthOfYear monthOfYear, Year year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
    }

    public ShowCommand(MonthOfYear monthOfYear) {
        this.monthOfYear = monthOfYear;

        // get current year
        java.util.Calendar currentDate = java.util.Calendar.getInstance();
        int currentYear = currentDate.get(java.util.Calendar.YEAR);
        this.year = new Year(currentYear);
    }

    public CommandResult execute(Calendar calendar) {
        Month requestedMonth = new Month(monthOfYear, year);
        calendar.updateMonthShown(requestedMonth);
        String formattedFeedback = String.format(MESSAGE_SHOWING_REQUESTED, monthOfYear.toString(), year.toString());
        return new CommandResult(formattedFeedback);
    }
}
