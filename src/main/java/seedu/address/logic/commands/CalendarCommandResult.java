package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.model.time.YearMonth;
import seedu.address.model.time.YearMonthDay;

/**
 * Represents the result of a calendar command execution.
 */
public class CalendarCommandResult extends CommandResult {

    private YearMonth yearMonth;
    private Optional<YearMonthDay> yearMonthDay;
    private boolean isShowingWeek;

    /**
     * Constructs a {@code CalendarCommandResult} with the specified fields.
     */
    public CalendarCommandResult(String feedbackToUser, YearMonth yearMonth) {
        super(feedbackToUser);
        this.yearMonth = yearMonth;
        yearMonthDay = Optional.empty();
        this.isShowingWeek = false;
    }

    /**
     * Constructs a {@code CalendarCommandResult} with the specified fields.
     */
    public CalendarCommandResult(String feedbackToUser, YearMonthDay yearMonthDay, boolean isShowingWeek) {
        super(feedbackToUser);
        this.yearMonth = yearMonthDay.getYearMonth();
        this.yearMonthDay = Optional.of(yearMonthDay);
        this.isShowingWeek = isShowingWeek;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public Optional<YearMonthDay> getYearMonthDay() {
        return yearMonthDay;
    }

    public boolean isShowingWeek() {
        return isShowingWeek;
    }

    @Override
    public boolean isCalendar() {
        return true;
    }
}
