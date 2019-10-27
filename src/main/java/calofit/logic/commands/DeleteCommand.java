package calofit.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.meal.Meal;


/**
 * Deletes a dish identified using it's displayed index from the dish database.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the dish identified by the index number used in the displayed dish list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEAL_SUCCESS = "Deleted Dish: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Meal> lastShownList = model.getMealLog().getTodayMeals();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
        }

        Meal mealToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.getMealLog().removeMeal(mealToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_MEAL_SUCCESS, mealToDelete.getDish().getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
