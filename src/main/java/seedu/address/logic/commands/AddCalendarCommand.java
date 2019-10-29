package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.calendar.CalendarWrapper;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

import java.util.Iterator;


/**
 * Adds a task to the address book.
 */
public class AddCalendarCommand extends Command {

    public static final String COMMAND_WORD = "add-calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a calendar. "
            + "Parameters: "
            + PREFIX_MEMBER_NAME + "MEMBER_NAME "
            + PREFIX_FILE_PATH + "FILE/PATH.ics "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER_NAME + "Elliot "
            + PREFIX_FILE_PATH + "C:\\Users\\gbrls\\Downloads";

    public static final String MESSAGE_SUCCESS = "New calendar added: %1$s";
    public static final String MESSAGE_DUPLICATE_CALENDAR = "This calendar already exists in the address book";

    private final CalendarWrapper toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddCalendarCommand(CalendarWrapper toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendar(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CALENDAR);
        }

        model.addCalendar(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCalendarCommand // instanceof handles nulls
                && toAdd.equals(((AddCalendarCommand) other).toAdd));
    }
}
