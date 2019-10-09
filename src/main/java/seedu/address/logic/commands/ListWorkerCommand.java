package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKERS;

import seedu.address.model.Model;

//@@author bernicechio
/**
 * Lists all workers in Mortago to the manager.
 */
public class ListWorkerCommand extends ListCommand {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all workers";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWorkerList(PREDICATE_SHOW_ALL_WORKERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListWorkerCommand); // instanceof handles nulls
    }
}
