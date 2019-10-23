package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;

/**
 * Deletes a food identified using it's displayed index from the menu.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the food identified by the index number used in the displayed food list.\n"
            + "Parameters: INDEX [OPTIONAL INDEXES] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FOOD_SUCCESS = "Deletion Successful!";
    public static final String NO_FOOD_TO_DELETE_ERROR = "There is no food to delete!";
    private final List<Index> targetIndexes;

    public DeleteCommand(List<Index> targetIndexes) {
        Collections.sort(targetIndexes);
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodList();

        if (model.getFilteredFoodList().size() == 0) {
            throw new CommandException(NO_FOOD_TO_DELETE_ERROR);
        }

        for (int i = targetIndexes.size() - 1; i >= 0; i--) {
            Index targetIndex = targetIndexes.get(i);
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
            }

            Food foodToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteFood(foodToDelete);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_FOOD_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndexes.equals(((DeleteCommand) other).targetIndexes)); // state check
    }
}
