package seedu.sugarmummy.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_DAY;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_YEAR_MONTH_WEEK;

import java.util.Optional;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.time.YearMonth;
import seedu.sugarmummy.model.time.YearMonthDay;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Shows calendar of a month.
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a calendar of a month with a list of calendar "
            + "entries on a day/ in a week/in a month."
            + "Parameters: "
            + "[" + PREFIX_YEAR_MONTH + "YEAR_MONTH]"
            + "[" + PREFIX_YEAR_MONTH_DAY + "YEAR_MONTH_DAY]"
            + "[" + PREFIX_YEAR_MONTH_WEEK + "YEAR_MONTH_WEEK]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_YEAR_MONTH + "2019-12";

    public static final String MESSAGE_SUCCESS_YEAR_MONTH = "Here's the calendar of %1$s that I'm showing you! "
            + "Any reminders and events in %1$s that you've added will be indicated here.";
    public static final String MESSAGE_SUCCESS_YEAR_MONTH_DAY = "Here's the calendar of %1$s that I'm showing you! "
            + "Any reminders and events on %2$s that you've added will be indicated here.";
    public static final String MESSAGE_SUCCESS_YEAR_MONTH_WEEK = "Here's the calendar of %1$s that I'm showing you! "
            + "Any reminders and events in the week containing %2$s that you've added will be indicated here.";
    public static final String MESSAGE_SUCCESS_RAW = "Here are all the reminders and events that you have added.";

    private final YearMonth yearMonth;
    private final Optional<YearMonthDay> yearMonthDay;
    private final boolean isShowingWeek;
    private final boolean isShowingRaw;

    public CalendarCommand() {
        yearMonth = null;
        yearMonthDay = null;
        isShowingWeek = false;
        isShowingRaw = true;
    }

    /**
     * Creates an CalendarCommand to show the specified {@code yearMonth} and a list of calendar entries.
     */
    public CalendarCommand(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        this.yearMonthDay = Optional.empty();
        isShowingWeek = false;
        isShowingRaw = false;
    }

    /**
     * Creates an CalendarCommand to show the specified year month of the given year month day and a list of calendar
     * entries of a day or a week.
     */
    public CalendarCommand(YearMonthDay yearMonthDay, boolean isShowingWeek) {
        this.yearMonth = yearMonthDay.getYearMonth();
        this.yearMonthDay = Optional.of(yearMonthDay);
        this.isShowingWeek = isShowingWeek;
        isShowingRaw = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isShowingRaw) {
            return new CommandResult(MESSAGE_SUCCESS_RAW);
        }
        if (yearMonthDay.isPresent()) {
            String feedback = String.format(MESSAGE_SUCCESS_YEAR_MONTH_DAY, yearMonth, yearMonthDay.get());
            if (isShowingWeek) {
                feedback = String.format(MESSAGE_SUCCESS_YEAR_MONTH_WEEK, yearMonth, yearMonthDay.get());
            }
            return new CalendarCommandResult(feedback, yearMonthDay.get(), isShowingWeek);
        } else {
            return new CalendarCommandResult(String.format(MESSAGE_SUCCESS_YEAR_MONTH, yearMonth), yearMonth);
        }
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        if (isShowingRaw) {
            return DisplayPaneType.CALENDAR_ENTRY;
        } else {
            return DisplayPaneType.CALENDAR_MONTH;
        }
    }

    @Override
    public boolean isToCreateNewPane() {
        return true;
    }
}
