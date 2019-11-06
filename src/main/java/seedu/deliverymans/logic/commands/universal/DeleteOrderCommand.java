package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Deletes an order identified using its displayed index from the address book.
 */
public class DeleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "-delete_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " Order 1";

    private static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";

    private final Name targetOrder;

    public DeleteOrderCommand(Name targetOrder) {
        this.targetOrder = targetOrder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Order orderToDelete = model.getOrder(targetOrder);

        if (orderToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_NAME);
        }

        model.deleteOrderInCustomer(orderToDelete);
        model.deleteOrder(orderToDelete);
        model.updateDeliverymanStatusAfterChangesToOrder(orderToDelete.getDeliveryman());
        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteOrderCommand // instanceof handles nulls
                && targetOrder.equals(((DeleteOrderCommand) other).targetOrder)); // state check
    }
}
