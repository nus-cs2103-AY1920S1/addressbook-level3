package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.PatientBook;

/**
 * Clears the patient book.
 */
public class ClearCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "pat-clear";
    public static final String MESSAGE_SUCCESS = "Patient book has been cleared!";
    public static final String MESSAGE_INVALID_PATIENT_HAS_ONGOING_VISIT =
            "There is a patient with an ongoing visit. Please finish the visit before executing this operation.";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getOngoingVisit().isPresent()) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_HAS_ONGOING_VISIT);
        }
        model.setStagedPatientBook(new PatientBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
