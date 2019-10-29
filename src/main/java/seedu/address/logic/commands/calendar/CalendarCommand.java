package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_WEEK;

import java.util.Optional;

import seedu.address.logic.commands.CalendarCommandResult;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.YearMonth;
import seedu.address.model.calendar.YearMonthDay;
import seedu.address.ui.DisplayPaneType;

/**
 * Shows calendar of a month.
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a calendar of a month with a list of calendar "
        + "entries on a day/ in a week/in the month."
        + "Parameters: "
        + "[" + PREFIX_YEAR_MONTH + "YEAR_MONTH]"
        + "[" + PREFIX_YEAR_MONTH_DAY + "YEAR_MONTH_DAY]"
        + "[" + PREFIX_YEAR_MONTH_WEEK + "YEAR_MONTH_WEEK]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_YEAR_MONTH + "2019-12";

    public static final String MESSAGE_SUCCESS = "Calendar of %1$s shown";

    private final YearMonth yearMonth;
    private final Optional<YearMonthDay> yearMonthDay;
    private final boolean isShowingWeek;

    /**
     * Creates an CalendarCommand to show the specified {@code yearMonth} and a list of calendar entries.
     */
    public CalendarCommand(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        this.yearMonthDay = Optional.empty();
        isShowingWeek = false;
    }

    /**
     * Creates an CalendarCommand to show the specified year month of the given year month day and a list of calendar
     * entries of a day or a week.
     */
    public CalendarCommand(YearMonthDay yearMonthDay, boolean isShowingWeek) {
        this.yearMonth = yearMonthDay.getYearMonth();
        this.yearMonthDay = Optional.of(yearMonthDay);
        this.isShowingWeek = isShowingWeek;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (yearMonthDay.isPresent()) {
            return new CalendarCommandResult(String.format(MESSAGE_SUCCESS, yearMonth),
                yearMonthDay.get(), isShowingWeek);
        } else {
            return new CalendarCommandResult(String.format(MESSAGE_SUCCESS, yearMonth), yearMonth);
        }
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.CALENDAR_MONTH;
    }

    @Override
    public boolean getNewPaneIsToBeCreated() {
        return true;
    }
}
