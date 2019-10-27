package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_MONTH;

import seedu.address.logic.commands.CalendarCommandResult;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.YearMonth;
import seedu.address.ui.DisplayPaneType;

/**
 * Shows calendar of a month.
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a calendar of a month. "
            + "Parameters: "
            + "[" + PREFIX_YEAR_MONTH + "YEARMONTH]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_YEAR_MONTH + "2019-12";

    public static final String MESSAGE_SUCCESS = "Calendar of %1$s shown";

    private final YearMonth yearMonth;

    /**
     * Creates an CalendarCommand to show the specified {@code yearMonth}
     */
    public CalendarCommand(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CalendarCommandResult(String.format(MESSAGE_SUCCESS, yearMonth), yearMonth);
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
