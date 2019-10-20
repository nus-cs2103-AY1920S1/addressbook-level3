package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_REPETITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Reminder;

/**
 * Adds a reminder to the calendar.
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the calendar. "
            + "Parameters: "
            + PREFIX_CALENDAR_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATETIME + "DATETIME "
            + "[" + PREFIX_CALENDAR_REPETITION + "REPETITION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CALENDAR_DESCRIPTION + "Insulin injection "
            + PREFIX_DATETIME + "31/5/2020 16:00 "
            + PREFIX_CALENDAR_REPETITION + "everyday";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the calendar";

    private final Reminder toAdd;

    /**
     * Creates an ReminderCommand to add the specified {@code Reminder}
     */
    public ReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendarEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        model.addCalendarEntry(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand // instanceof handles nulls
                && toAdd.equals(((ReminderCommand) other).toAdd));
    }
}
