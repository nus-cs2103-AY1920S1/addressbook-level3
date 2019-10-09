package calofit.logic.commands;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Dish;
import calofit.model.meal.Meal;
import calofit.model.util.Timestamp;
import java.time.LocalDateTime;

public class AddMealCommand extends Command {
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Dish d = model.getDishDatabase().getDishList().get(0);
        model.addMeal(new Meal(d, new Timestamp(LocalDateTime.now())));
        return new CommandResult("ok");
    }
}
