package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;

/**
 * Sorts the appointments in VISIT.
 */
public class SortAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the appointments. ";

    public static final String MESSAGE_SUCCESS = "Appointments sorted.";

    /**
     * Creates a SortAppointmentsCommand to sort the Appointments
     */
    public SortAppointmentsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
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
