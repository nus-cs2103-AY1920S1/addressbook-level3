package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Reminder;

/**
 * Adds a reminder to VISIT.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new reminder to show up when the program is launched. "
            + "Parameters: "
            + "[" + PREFIX_DAYS + "EXPIRY IN DAYS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Two Point Hospital closed "
            + PREFIX_DAYS + "7";

    public static final String MESSAGE_SUCCESS = "New reminder added: %s";

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

        model.addReminder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getDescription()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand // instanceof handles nulls
                && toAdd.equals(((ReminderCommand) other).toAdd));
    }
}
