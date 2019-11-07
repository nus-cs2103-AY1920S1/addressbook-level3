package cs.f10.t1.nursetraverse.logic.commands.appointment;

import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.logic.commands.Command;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.model.Model;

/**
 * Lists all appointments in the appointment book.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appt-list";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
