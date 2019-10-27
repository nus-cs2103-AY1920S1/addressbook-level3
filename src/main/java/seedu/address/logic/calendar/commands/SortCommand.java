package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.model.calendar.CalendarModel;

/**
 * Sort the list of tasks by either TaskDeadline or the time when the task is added
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sort the tasks according to 'deadline' or 'timeadded'\n"
        + "Parameters: 'deadline' or 'timeadded'\n"
        + "Example: " + COMMAND_WORD + " deadline";

    public static final String MESSAGE_SORT_SUCCESS = "List sorted by %1$s";

    private final String sortType;

    public SortCommand(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public CommandResult execute(CalendarModel calendarModel) throws CommandException {
        requireNonNull(calendarModel);
        calendarModel.switchSortType(sortType);
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortCommand // instanceof handles nulls
            && sortType.equals(((SortCommand) other).sortType)); // state check
    }
}
