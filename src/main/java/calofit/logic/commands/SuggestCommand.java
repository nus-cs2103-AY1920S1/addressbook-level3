package calofit.logic.commands;

import calofit.model.dish.CaloriesPredicate;
import calofit.model.Model;

public class SuggestCommand extends Command{

    public static final String COMMAND_WORD = "suggest";

    public static final String MESSAGE_SUCCESS = "These are the suggested meals";

    public static final String EMPTY_SUGGEST = "Sorry, you have hit your daily limit, no suggestion could be made.";

    @Override
    public CommandResult execute(Model model) {
        String stringBuilder = "";

        if(model.suggestMeal() == null) {
            return new CommandResult(EMPTY_SUGGEST);
        } else {
            for (int i = 0; i < model.suggestMeal().size(); i++) {
                stringBuilder += model.suggestMeal().get(i).getName();
            }
        }

        CaloriesPredicate predicate = new CaloriesPredicate(
            model.getCalorieBudget().getCurrentBudget().getAsInt());

        model.updateFilteredDishList(predicate);

        return new CommandResult(MESSAGE_SUCCESS + stringBuilder);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SuggestCommand); // instanceof handles nulls
    }

}