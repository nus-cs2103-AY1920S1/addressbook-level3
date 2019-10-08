package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BODIES;

import seedu.address.model.Model;

//@@author bernicechio
/**
 * Lists all bodies in Mortago to the manager.
 */
public class ListBodyCommand extends ListCommand {
    public static final String MESSAGE_SUCCESS = "Listed all bodies";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBodyList(PREDICATE_SHOW_ALL_BODIES);
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
