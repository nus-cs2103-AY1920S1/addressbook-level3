package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts all persons in the team list according to alphabetical order. Sorting is case insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted your team list in alphabetical order.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortAthletickByName();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public String toString() {
        return "Sort Command";
    }
}
