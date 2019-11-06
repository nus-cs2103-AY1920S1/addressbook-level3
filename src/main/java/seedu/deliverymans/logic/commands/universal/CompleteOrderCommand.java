package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Tofill.
 */
public class CompleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "-complete_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the order identified by the index used in the displayed order list.\n"
            + "Parameters: ORDERNAME\n"
            + "Example: " + COMMAND_WORD + "Order 1";

    private static final String MESSAGE_COMPLETE_ORDER_SUCCESS = "Order completed: %1$s";
    private static final String MESSAGE_ALREADY_COMPLETED = "The order is already completed!";
    private static final String MESSAGE_INVALID_DELIVERYMAN = "The order needs "
            + "an assigned deliveryman before it can be completed!";
    private final Name targetOrder;

    public CompleteOrderCommand(Name targetOrder) {
        this.targetOrder = targetOrder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Order orderToComplete = model.getOrder(targetOrder);

        if (orderToComplete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_NAME);
        }

        if (orderToComplete.getDeliveryman().fullName.equalsIgnoreCase("Unassigned")) {
            throw new CommandException(MESSAGE_INVALID_DELIVERYMAN);
        }
        if (orderToComplete.isCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        Order order = new Order.OrderBuilder().setOrderName(orderToComplete.getOrderName())
                .setCustomer(orderToComplete.getCustomer())
                .setDeliveryman(orderToComplete.getDeliveryman())
                .setRestaurant(orderToComplete.getRestaurant())
                .setFood(orderToComplete.getFoodList()).setCompleted(true).completeOrder();
        model.setOrder(orderToComplete, order);
        model.updateDeliverymanStatusAfterChangesToOrder(order.getDeliveryman());

        List<Restaurant> restaurants = model.getFilteredRestaurantList();
        for (Restaurant r : restaurants) {
            if (r.getName().equals(orderToComplete.getRestaurant())) {
                r.updateQuantity(orderToComplete);
            }
        }

        return new CommandResult(String.format(MESSAGE_COMPLETE_ORDER_SUCCESS, orderToComplete));
    }
}
