package calofit.logic.commands;

import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.exceptions.DuplicateDishException;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Timestamp;

import java.time.LocalDateTime;


/**
 * Adds a dish to the dish database.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a dish to the dish database. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Carbonara "
            + PREFIX_TAG + "low calories "
            + PREFIX_TAG + "salty";

    public static final String MESSAGE_SUCCESS = "New dish added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEAL = "This dish already exists in the dish database";
    public static final String MESSAGE_MEAL_NOT_IN_DATABASE = "This dish is not in our database. " +
            "Please update the calories using the c/ " +
            "calories set to 700 by default";

    private final Dish toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Dish}
     */
    public AddCommand(Dish dish) {
        requireNonNull(dish);
        toAdd = dish;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Dish wantToAdd = toAdd;
        MealLog mealLog = model.getMealLog();

        if (model.hasDish(wantToAdd)) {
            wantToAdd = model.getDishByName(toAdd);
            Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
            mealLog.addMeal(toAddMeal);

            throw new CommandException(MESSAGE_DUPLICATE_MEAL);
        } else {

            if (model.hasDishName(wantToAdd) && !wantToAdd.getCalories().equals(new Calorie("700"))) {
                model.addDish(wantToAdd);
                Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);

            } else if (model.hasDishName(wantToAdd) && wantToAdd.getCalories().equals(new Calorie("700"))) {
                wantToAdd = model.getDishByName(toAdd);
                if(!wantToAdd.getCalories().equals(toAdd.getCalories())) {
                    wantToAdd = toAdd;
                }
                try {
                    model.addDish(wantToAdd);
                } catch (DuplicateDishException e) {
                    System.out.println("There is another Dish with the same name");
                }
                Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);
            } else {
                model.addDish(wantToAdd);
                Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);
            }
        }

//        model.addDish(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, wantToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
