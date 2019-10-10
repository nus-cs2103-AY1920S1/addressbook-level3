package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts all persons in the team list according to alphabetical order. Sorting is case insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted your team list in alphabetical order";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortAddressBookByName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
