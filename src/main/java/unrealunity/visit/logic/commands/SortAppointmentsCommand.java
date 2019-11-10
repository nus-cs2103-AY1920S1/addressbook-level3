package unrealunity.visit.logic.commands;

import static java.util.Objects.requireNonNull;

import unrealunity.visit.model.Model;

/**
 * Sorts the appointments in VISIT.
 */
public class SortAppointmentsCommand extends Command {

    /**
     * Word to call the Follow-Up Command.
     */
    public static final String COMMAND_WORD = "sort";

    /**
     * Help message on usage.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the appointments. ";

    /**
     * Success message when executed.
     */
    public static final String MESSAGE_SUCCESS = "Appointments sorted.";

    /**
     * Creates a SortAppointmentsCommand to sort the Appointments
     */
    public SortAppointmentsCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.sortAppointments();
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortAppointmentsCommand); // instanceof handles nulls
    }
}
