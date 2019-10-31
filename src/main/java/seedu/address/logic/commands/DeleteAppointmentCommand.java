package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;

import seedu.address.model.Model;

/**
 * Deletes an Appointment from VISIT.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "removeappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an appointment from VISIT. "
            + "Parameters: "
            + "[" + PREFIX_DAYS + "EXPIRY IN DAYS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Two Point Hospital closed "
            + PREFIX_DAYS + "7";

    public static final String MESSAGE_SUCCESS = "Appointment deleted.";

    private final String description;
    private final int days;

    /**
     * Creates a DeleteAppointmentCommand to delete the specified {@code Appointment}
     */
    public DeleteAppointmentCommand(String description, int days) {
        requireNonNull(description);

        this.description = description;
        this.days = days;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.deleteAppointment(description, days);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, description, days));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && description.equals(((DeleteAppointmentCommand) other).description)
                && days == ((DeleteAppointmentCommand) other).days);
    }
}
