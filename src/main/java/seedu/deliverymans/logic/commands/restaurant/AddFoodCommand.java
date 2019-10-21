package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Adds a food to the current restaurant.
 */
public class AddFoodCommand extends Command {
    public static final String COMMAND_WORD = "addfood";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the current restaurant. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Fried Chicken "
            + PREFIX_PRICE + "7.65 "
            + PREFIX_TIME + "300 "
            + PREFIX_TAG + "FastFood";

    public static final String MESSAGE_SUCCESS = "New food added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD =
            "This food already exists in the restaurant";

    private final Food toAdd;

    /**
     * Creates a FoodCommand to add the specified {@code Food}
     */
    public AddFoodCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Restaurant restaurant = model.getEditingRestaurantList().get(0);
        boolean isDuplicate = restaurant.getMenu().stream().anyMatch(toAdd::isSameFood);
        if (isDuplicate) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }
        restaurant.addFood(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFoodCommand // instanceof handles nulls
                && toAdd.equals(((AddFoodCommand) other).toAdd));
    }
}
