package calofit.logic.commands;

import calofit.model.Model;

public class SuggestCommand extends Command{

    public static final String COMMAND_WORD = "suggest";

    public static final String MESSAGE_SUCCESS = "These are the suggested meals";


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