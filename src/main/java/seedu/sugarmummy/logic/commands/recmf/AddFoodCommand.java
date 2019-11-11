package seedu.sugarmummy.logic.commands.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_TYPE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_GI;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SUGAR;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.recmf.FoodComparator;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Adds a food to the food recommendation list.
 */
public class AddFoodCommand extends Command {

    public static final String COMMAND_WORD = "addfood";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the food recommendation list.\n"
            + "Parameters: " + PREFIX_FOOD_NAME + "FOOD_NAME " + PREFIX_FOOD_TYPE + "FOOD_TYPE "
            + PREFIX_CALORIE + "CALORIE " + PREFIX_GI + "GI " + PREFIX_SUGAR + "SUGAR " + PREFIX_FAT + "FAT\n"
            + "Food type can be one of the following:\n"
            + "nsv: non-starchy vegetables; sv: starchy vegetables; f: fruit; p: protein; s: snack; m: meal\n"
            + "CALORIE, GI, SUGAR, and FAT are all numbers.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FOOD_NAME + "Cucumber " + PREFIX_FOOD_TYPE + "nsv "
            + PREFIX_CALORIE + "15 " + PREFIX_GI + "15 " + PREFIX_SUGAR + "1.7 " + PREFIX_FAT + "0\n";

    public static final String MESSAGE_SUCCESS = "I've successfully added new food: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "Oops! This food already exists.";

    private final Food toAdd;

    /**
     * Creates an AddFoodCommand to add the specified {@code food}
     */
    public AddFoodCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFood(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.addFood(toAdd);
        model.sortFoodList(new FoodComparator(FoodComparator.DEFAULT_SORT_ORDER_STRING));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.CHANGE_FOOD;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddFoodCommand
                && toAdd.equals(((AddFoodCommand) other).toAdd));
    }
}
