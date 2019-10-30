package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DRIVERS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Resets all the Task's / Driver's / Customer's list to original view.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        //reset any predicate that customer list has
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        //reset any predicate that driver list has
        model.updateFilteredDriverList(PREDICATE_SHOW_ALL_DRIVERS);
        //reset any predicate that assigned and unassigned task list has
        model.getUnassignedTaskList();
        model.getAssignedTaskList();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
