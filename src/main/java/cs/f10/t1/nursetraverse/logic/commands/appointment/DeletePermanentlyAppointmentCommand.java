package cs.f10.t1.nursetraverse.logic.commands.appointment;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;

/**
 * Deletes permanently a recurring appointment identified using it's displayed index from the appointment book.
 */
public class DeletePermanentlyAppointmentCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "appt-delete-permanent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment (permanently if recurring) as identified by the index number used in the "
            + "displayed appointment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    private final Index targetIndex;

    public DeletePermanentlyAppointmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> fullAppointmentList = model.getStagedAppointmentList();

        if (targetIndex.getZeroBased() >= fullAppointmentList.size()) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToDelete = fullAppointmentList.get(targetIndex.getZeroBased());
        model.deleteRecurringAppointment(appointmentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePermanentlyAppointmentCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePermanentlyAppointmentCommand) other).targetIndex)); // state check
    }
}
