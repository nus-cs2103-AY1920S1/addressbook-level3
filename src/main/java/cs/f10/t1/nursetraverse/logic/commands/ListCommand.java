package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.model.Model;

/**
 * Lists all patients in the patient book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "pat-list";

    public static final String MESSAGE_SUCCESS = "Listed all patients";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(Model.PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
