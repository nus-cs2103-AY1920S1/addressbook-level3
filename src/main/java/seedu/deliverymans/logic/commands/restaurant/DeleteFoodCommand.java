package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Deletes a food item identified using its displayed index from the menu list.
 */
public class DeleteFoodCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the food item identified by the index number used in the displayed menu list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RESTAURANT_SUCCESS = "Deleted Food: %1$s";

    private final Index targetIndex;

    public DeleteFoodCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Restaurant restaurant = model.getEditingRestaurantList().get(0);
        if (targetIndex.getZeroBased() >= restaurant.getMenu().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }
        Food foodToDelete = restaurant.getMenu().get(targetIndex.getZeroBased());

        model.setRestaurant(restaurant, restaurant
                .removeFood(foodToDelete)
                .updateQuantity(foodToDelete.getQuantityOrdered() * -1));
        return new CommandResult(String.format(MESSAGE_DELETE_RESTAURANT_SUCCESS, foodToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFoodCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteFoodCommand) other).targetIndex)); // state check
    }
}
