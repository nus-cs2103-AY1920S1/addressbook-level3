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
 * Tofill.
 */
public class CompleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "-complete_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the order identified by the index used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_COMPLETE_ORDER_SUCCESS = "Order completed: %1$s";
    private static final String MESSAGE_ALREADY_COMPLETED = "The order is already completed!";
    private static final String MESSAGE_INVALID_DELIVERYMAN = "The order needs "
            + "an assigned deliveryman before it can be completed!";
    private final Index targetIndex;

    public CompleteOrderCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToComplete = lastShownList.get(targetIndex.getZeroBased());
        if (orderToComplete.getDeliveryman().fullName.equalsIgnoreCase("Unassigned")) {
            throw new CommandException(MESSAGE_INVALID_DELIVERYMAN);
        }
        if (orderToComplete.isCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        Order order = new Order.OrderBuilder().setCustomer(orderToComplete.getCustomer())
                .setDeliveryman(orderToComplete.getDeliveryman())
                .setRestaurant(orderToComplete.getRestaurant())
                .setFood(orderToComplete.getFoodList()).setCompleted(true).completeOrder();
        model.setOrder(orderToComplete, order);

        return new CommandResult(String.format(MESSAGE_COMPLETE_ORDER_SUCCESS, orderToComplete));
    }
}
