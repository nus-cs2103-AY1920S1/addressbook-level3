package seedu.deliverymans.logic.commands.customer;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.customer.Customer;

/**
 * List orders of customer.
 */
public class ListOrderCommand extends Command {
    public static final String COMMAND_WORD = "listorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the customer's orders identified by the index number used in the displayed customer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LIST_ORDERS_SUCCESS = "Listed Customer's orders: %1$s";

    private final Index targetIndex;

    public ListOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Customer customerToList = lastShownList.get(targetIndex.getZeroBased());
        model.setCustomerOrders(customerToList);
        return new CommandResult(String.format(MESSAGE_LIST_ORDERS_SUCCESS, customerToList), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListOrderCommand // instanceof handles nulls
                && targetIndex.equals(((ListOrderCommand) other).targetIndex)); // state check
    }
}
