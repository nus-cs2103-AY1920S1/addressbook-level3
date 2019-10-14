package calofit.logic.commands;

import calofit.model.Model;

/**
 * Suggest meal for user according to calorie budget left.
 */
public class SuggestCommand extends Command {

    public static final String COMMAND_WORD = "suggest";

    public static final String MESSAGE_SUCCESS = "The suggested meals are listed below!";


    @Override
    public CommandResult execute(Model model) {

        int remain = model.getRemainingCalories();
        model.updateFilteredDishList(dish -> dish.getCalories().getValue() <= remain);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SuggestCommand); // instanceof handles nulls
    }

}
