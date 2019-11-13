package calofit.logic.commands;

import calofit.model.Model;

/**
 * Suggest meal for user according to calorie budget left.
 */
public class SuggestCommand extends Command {

    public static final String COMMAND_WORD = "suggest";

    public static final String MESSAGE_SUCCESS = "The suggest feature has been toggled.";


    @Override
    public CommandResult execute(Model model) {

        int remain = model.getRemainingCalories();
        model.setDishFilterPredicate(null);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
