package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Returned all the Task's / Driver's / Customer's list "
            + "to its original view.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        //reset any predicate that customer list has
        //reset any predicate that driver list has
        //reset any predicate that assigned and unassigned task list has
        model.refreshAllFilteredList();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
