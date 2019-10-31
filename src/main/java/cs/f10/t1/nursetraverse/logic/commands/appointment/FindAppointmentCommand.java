package cs.f10.t1.nursetraverse.logic.commands.appointment;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW;
import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.logic.commands.Command;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.appointment.AppointmentContainsKeywordsPredicate;

/**
 * Finds and lists all appointments in appointment book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appt-find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all appointments whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final AppointmentContainsKeywordsPredicate predicate;

    public FindAppointmentCommand(AppointmentContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);
        return new CommandResult(
                String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAppointmentCommand // instanceof handles nulls
                && predicate.equals(((FindAppointmentCommand) other).predicate)); // state check
    }
}


