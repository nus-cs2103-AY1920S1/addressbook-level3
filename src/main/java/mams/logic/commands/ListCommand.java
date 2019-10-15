package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import mams.model.Model;

/**
 * Lists all students in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
