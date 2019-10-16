package seedu.deliverymans.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.model.Model;

/**
 * (to be added)
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all customers";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
