package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;

import javafx.collections.ObservableList;
import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Adds a food to the current restaurant.
 */
public class AddFoodCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the current restaurant. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Fried Chicken "
            + PREFIX_PRICE + "7.65 "
            + PREFIX_TAG + "Recommended";

    public static final String MESSAGE_SUCCESS = "New food added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD =
            "This food already exists in the restaurant";
    public static final String MESSAGE_WRONG_TAG =
            "You can only tag food as \"Recommended\"";

    private final Food toAdd;

    /**
     * Creates a AddFoodCommand to add the specified {@code Food}
     */
    public AddFoodCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);
        Restaurant restaurant = model.getEditingRestaurantList().get(0);

        ObservableList<Tag> newTags = toAdd.getTags();
        if (newTags.size() > 1) {
            throw new CommandException(MESSAGE_WRONG_TAG);
        } else if (newTags.size() == 1) {
            if (!newTags.get(0).tagName.equals("Recommended")) {
                throw new CommandException(MESSAGE_WRONG_TAG);
            }
        }

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
