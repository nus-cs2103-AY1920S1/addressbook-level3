package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GI;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUGAR;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.DisplayPaneType;
import sugarmummy.recmfood.model.Food;

/**
 * Adds a food to the food recommendation list.
 */
public class AddFoodCommand extends Command {

    public static final String COMMAND_WORD = "addfood";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the food recommendation list.\n"
        + "Parameters: " + PREFIX_FOOD + "FOOD_NAME " + PREFIX_FOOD_TYPE + "FOOD_TYPE "
        + PREFIX_CALORIE + "CALORIE " + PREFIX_GI + "GI " + PREFIX_SUGAR + "SUGAR " + PREFIX_FAT + "FAT\n"
        + "Food type can be one of the following:\n"
        + "nsv: non-starchy vegetables; sv: starchy vegetables; f: fruit; p: protein; s: snack; m: meal\n"
        + "Calorie(Cal), GI, sugar(g), and fat(g) are all numbers.\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_FOOD + "Cherry " + PREFIX_FOOD_TYPE + "f "
        + PREFIX_CALORIE + "63 " + PREFIX_GI + "20 " + PREFIX_SUGAR + "12 " + PREFIX_FAT + "0\n";

    public static final String MESSAGE_SUCCESS = "New food added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists";

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
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.RECM_FOOD;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AddFoodCommand
            && toAdd.equals(((AddFoodCommand) other).toAdd));
    }
}
