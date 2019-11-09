package calofit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;

import calofit.commons.core.Messages;
//import calofit.commons.core.index.Index;
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
            + "Parameters: INDEX(S) (must be a positive integer between 1 and 2 billion that is in the meal list)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEAL_SUCCESS = "Deleted Meal: %1$s";


    private List<Integer> listOfIndex;

    private boolean isList = false;

    public DeleteCommand(List<Integer> listOfIndex) {
        this.listOfIndex = listOfIndex;
        this.isList = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Meal> lastShownList = model.getMealLog().getTodayMeals();

        String listOfMealToDeleteToString = "";
        int indexCounter = 1;
        Collections.sort(listOfIndex, Comparator.reverseOrder());

        for (int lastIndex : listOfIndex) {
            if (lastIndex >= lastShownList.size()) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_MEAL_INDEX, lastIndex + 1));
            }
            Meal mealToDelete = lastShownList.get(lastIndex);
            model.removeMeal(mealToDelete);

            listOfMealToDeleteToString = listOfMealToDeleteToString + "\n"
                    + indexCounter + ". " + mealToDelete.getDish();

            indexCounter++;
        }

        return new CommandResult(String.format(MESSAGE_DELETE_MEAL_SUCCESS, listOfMealToDeleteToString));
        //else {
        //    if (targetIndex.getZeroBased() >= lastShownList.size()) {
        //        throw new CommandException(Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
        //    }
        //
        //    Meal mealToDelete = lastShownList.get(targetIndex.getZeroBased());
        //    model.getMealLog().removeMeal(mealToDelete);
        //
        //    return new CommandResult(String.format(MESSAGE_DELETE_MEAL_SUCCESS, mealToDelete.getDish().getName()));
        //}
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && listOfIndex.equals(((DeleteCommand) other).listOfIndex)); // state check
    }
}
