package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List the completed delivery task of a particular customer.
 */
public class ViewCustomerTaskCommand extends Command {

    public static final String COMMAND_WORD = "viewC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the delivered tasks of the specified customer "
            + "and displays them as a list with index numbers."
            + "\n"
            + "Parameters: " + "CUSTOMER_ID (must be a positive integer)"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "1";

    public static final String MESSAGE_SUCCESS = "Listed %s completed tasks delivered to the Customer ID #%s";

    private int customerId;

    public ViewCustomerTaskCommand(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCustomer(customerId)) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        model.viewCustomerTask(customerId);
        int listSize = model.getCurrentCompletedTaskList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, listSize, customerId));
    }

}
