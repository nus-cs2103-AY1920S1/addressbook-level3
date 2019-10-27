package seedu.address.logic.commands;

import seedu.address.model.YearMonth;

/**
 * Represents the result of a calendar command execution.
 */
public class CalendarCommandResult extends CommandResult {

    private YearMonth yearMonth;
    /**
     * Constructs a {@code CalendarCommandResult} with the specified fields.
     */
    public CalendarCommandResult(String feedbackToUser, YearMonth yearMonth) {
        super(feedbackToUser);
        this.yearMonth = yearMonth;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    @Override
    public boolean isCalendar() {
        return true;
    }
}
