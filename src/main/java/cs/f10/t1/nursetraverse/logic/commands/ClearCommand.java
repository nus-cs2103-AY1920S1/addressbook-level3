package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.PatientBook;

/**
 * Clears the patient book.
 */
public class ClearCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "pat-clear";
    public static final String MESSAGE_SUCCESS = "Patient book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setStagedPatientBook(new PatientBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
