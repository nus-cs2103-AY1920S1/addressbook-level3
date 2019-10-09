package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

//@@author bernicechio
/**
 * Lists all fridges in Mortago to the manager.
 */
public class ListFridgeCommand extends ListCommand {
    private static final String MESSAGE_SUCCESS = "Listed all fridges";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListFridgeCommand); // instanceof handles nulls
    }
}
