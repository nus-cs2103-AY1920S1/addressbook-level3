package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a reminder to VISIT.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new reminder for the user to keep track. "
            + "Parameters: "
            + "[" + PREFIX_DAYS + "EXPIRY IN DAYS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Two Point Hospital closed "
            + PREFIX_DAYS + "7";

    public static final String MESSAGE_SUCCESS = "New reminder added: %s - %d day(s)";

    private final String description;
    private final int days;

    /**
     * Creates a ReminderCommand to add the specified {@code Reminder}
     */
    public ReminderCommand(String description, int days) {
        requireNonNull(description);

        this.description = description;
        this.days = days;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addAppointment(0, description, days);
        //model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, description, days));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand // instanceof handles nulls
                && description.equals(((ReminderCommand) other).description)
                && days == ((ReminderCommand) other).days);
    }
}
