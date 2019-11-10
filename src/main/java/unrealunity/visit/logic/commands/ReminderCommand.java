package unrealunity.visit.logic.commands;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DAYS;

import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.appointment.Appointment;

/**
 * Adds a reminder appointment to VISIT.
 */
public class ReminderCommand extends Command {

    /**
     * Word to call the Follow-Up Command.
     */
    public static final String COMMAND_WORD = "reminder";

    /**
     * Help message on usage.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new reminder for the user to keep track. "
            + "Parameters: "
            + "[" + PREFIX_DAYS + "EXPIRY IN DAYS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Two Point Hospital closed "
            + PREFIX_DAYS + "7";

    /**
     * Success message when executed.
     */
    public static final String MESSAGE_SUCCESS = "New reminder added: %s - %d day(s)";

    /**
     * The description of the reminder to store.
     */
    private final String description;

    /**
     * Optional number of days for how long the reminder lasts.
     */
    private final int days;

    /**
     * Creates a ReminderCommand to add the specified {@code Appointment}.
     *
     * @param description The description of the reminder to store.
     * @param days Number of days for when the follow-up occurs.
     */
    public ReminderCommand(String description, int days) {
        requireNonNull(description);

        this.description = description;
        this.days = days;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addAppointment(Appointment.Type.REMINDER, description, days);
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
