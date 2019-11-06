package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Assigns an order identified using its displayed index from the address book.
 */
public class AssignOrderCommand extends Command {

    public static final String COMMAND_WORD = "-assign_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the order identified by the index used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_ASSIGN_ORDER_SUCCESS = "Assigned Order: %1$s";

    private final Index targetIndex;

    public AssignOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToAssign = lastShownList.get(targetIndex.getZeroBased());

        if (orderToAssign.isCompleted() || !(orderToAssign.getDeliveryman().toString().equals(("Unassigned")))) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_TO_ASSIGN);
        }

        Name newDeliveryman = model.getOneAvailableDeliveryman();

        // to be added: assign new deliveryman using your orderbuilder etc

        return new CommandResult(String.format(MESSAGE_ASSIGN_ORDER_SUCCESS, orderToAssign));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignOrderCommand // instanceof handles nulls
                && targetIndex.equals(((AssignOrderCommand) other).targetIndex)); // state check
    }
}
