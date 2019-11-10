package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Deletes a restaurant identified using its displayed index from the restaurant list.
 */
public class DeleteRestaurantCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the restaurant identified by the index number used in the displayed restaurant list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RESTAURANT_SUCCESS = "Deleted Restaurant: %1$s";

    private final Index targetIndex;

    public DeleteRestaurantCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Restaurant> lastShownList = model.getFilteredRestaurantList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }
        Restaurant restaurantToDelete = lastShownList.get(targetIndex.getZeroBased());

        List<Order> allOrders = new ArrayList<>(model.getFilteredOrderList());
        for (Order order : allOrders) {
            if (order.getRestaurant().equals(restaurantToDelete.getName())) {
                model.deleteOrder(order);
                model.deleteOrderInCustomer(order);
                model.updateDeliverymanStatusAfterChangesToOrder(order.getDeliveryman());
            }
        }
        model.deleteRestaurant(restaurantToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_RESTAURANT_SUCCESS, restaurantToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRestaurantCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRestaurantCommand) other).targetIndex)); // state check
    }
}
