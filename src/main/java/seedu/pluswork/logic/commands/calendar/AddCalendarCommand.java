package seedu.pluswork.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.calendar.CalendarWrapper;


/**
 * Adds a task to the address book.
 */
public class AddCalendarCommand extends Command {

    public static final String COMMAND_WORD = "add-calendar";
    public static final String PREFIX_USAGE = PREFIX_MEMBER_NAME + " " + PREFIX_FILE_PATH;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a calendar. "
            + "Parameters: "
            + PREFIX_MEMBER_NAME + "MEMBER_NAME "
            + PREFIX_FILE_PATH + "C:/FILE/PATH.ics\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER_NAME + "Elliot "
            + PREFIX_FILE_PATH + "C:\\Users\\gbrls\\Downloads";

    public static final String MESSAGE_SUCCESS = "New calendar added: %1$s";
    public static final String MESSAGE_DUPLICATE_CALENDAR = "This member already has a calendar in " +
            "the address book. Please delete the existing calendar if you wish to change the calendar file.";

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
