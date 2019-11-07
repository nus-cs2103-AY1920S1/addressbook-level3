package unrealunity.visit.logic.commands;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DAYS;

import unrealunity.visit.model.Model;

/**
 * Deletes a specified Appointment from VISIT.
 */
public class DeleteAppointmentCommand extends Command {

    /**
     * Word to call the Delete Appointment Command.
     */
    public static final String COMMAND_WORD = "removeappt";

    /**
     * Help message on usage.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an appointment from VISIT. "
            + "Parameters: "
            + "[" + PREFIX_DAYS + "EXPIRY IN DAYS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Two Point Hospital closed "
            + PREFIX_DAYS + "7";

    /**
     * Success message when executed.
     */
    public static final String MESSAGE_SUCCESS = "Appointment deleted.";

    /**
     * The description of the appointment to delete.
     * This can be the name of the patient or the description of the reminder.
     */
    private final String description;

    /**
     * Optional number of days to specifically target the exact appointment to delete.
     */
    private final int days;

    /**
     * Creates a DeleteAppointmentCommand to delete the specified {@code Appointment}.
     *
     * @param description The description of the appointment to delete.
     * @param days Optional number of days to specifically target the exact appointment to delete.
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
