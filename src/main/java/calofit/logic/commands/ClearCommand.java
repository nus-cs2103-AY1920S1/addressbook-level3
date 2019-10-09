package calofit.logic.commands;

import static java.util.Objects.requireNonNull;

import calofit.model.Model;
import calofit.model.dish.DishDatabase;

/**
 * Clears the dish database.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Dish database has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setDishDatabase(new DishDatabase());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
