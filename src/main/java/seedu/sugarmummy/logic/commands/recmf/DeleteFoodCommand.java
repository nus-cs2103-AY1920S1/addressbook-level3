package seedu.sugarmummy.logic.commands.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.recmf.FoodName;
import seedu.sugarmummy.model.recmf.exceptions.FoodNotFoundException;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Deletes a food with specified food name.
 */
public class DeleteFoodCommand extends Command {

    public static final String COMMAND_WORD = "deletef";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the food identified by the food name.\n"
            + "Parameters: " + PREFIX_FOOD_NAME + "FOOD_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " fn/Chicken";

    public static final String MESSAGE_SUCCESS = "I've successfully deleted Food: %1$s";
    public static final String MESSAGE_CANNOT_FIND_FOOD = "Oops! I'm unable to find any food that matches %1$s";
    private final FoodName foodName;

    public DeleteFoodCommand(FoodName foodName) {
        this.foodName = foodName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.deleteFood(foodName);
        } catch (FoodNotFoundException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, foodName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFoodCommand // instanceof handles nulls
                && foodName.equals(((DeleteFoodCommand) other).foodName)); // state check
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.CHANGE_FOOD;
    }
}
