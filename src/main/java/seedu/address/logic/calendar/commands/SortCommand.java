package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.model.calendar.CalendarModel;

/**
 * Sort the list of tasks by either <code>TaskDeadline</code>, <code>TaskTime</code>, or <code>TaskTitle</code>.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sort the tasks according to 'deadline', 'time, or 'title'\n"
        + "Parameters: 'deadline', 'time', or 'title'\n"
        + "Example: " + COMMAND_WORD + " deadline";

    public static final String MESSAGE_SORT_SUCCESS = "Tasks sorted by %1$s";

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
