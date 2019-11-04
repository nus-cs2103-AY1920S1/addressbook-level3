package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.order.Order;

/**
 * Deletes an order identified using its displayed index from the address book.
 */
public class DeleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "-delete_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";

    private final Index targetIndex;

    public DeleteOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteOrder(orderToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteOrderCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteOrderCommand) other).targetIndex)); // state check
    }
}
