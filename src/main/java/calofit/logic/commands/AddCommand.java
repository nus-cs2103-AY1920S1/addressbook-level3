package calofit.logic.commands;

import static calofit.logic.parser.CliSyntax.PREFIX_CALORIES;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.exceptions.DuplicateDishException;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Timestamp;


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
            + PREFIX_CALORIES + "low calorie "
            + PREFIX_TAG + "salty";

    public static final String MESSAGE_SUCCESS = "New meal added to meal log: %1$s";
    public static final String MESSAGE_DUPLICATE_MEAL = "This dish already exists in the dish database";
    public static final String MESSAGE_MEAL_NOT_IN_DATABASE = "This dish is not in our database. "
            + "Please update the calories using the c/ "
            + "calories set to 700 by default";

    public static final int DEFAULT_MEAL_CALORIE = 700;

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

            return new CommandResult(String.format(MESSAGE_SUCCESS, wantToAdd));
            //throw new CommandException(MESSAGE_DUPLICATE_MEAL);
        } else {
            if (model.hasDishName(wantToAdd) && !wantToAdd.getCalories().equals(Calorie.UNKNOWN_CALORIE)) {
                model.addDish(wantToAdd);
                Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);

            } else if (model.hasDishName(wantToAdd)
                    && wantToAdd.getCalories().equals(Calorie.UNKNOWN_CALORIE)) {
                wantToAdd = model.getDishByName(toAdd);
                try {
                    model.addDish(wantToAdd);
                } catch (DuplicateDishException e) {
                    System.out.println("There is another Dish with the same name");
                }
                Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);
            } else if (!model.hasDishName(wantToAdd) && wantToAdd.getCalories().equals(Calorie.UNKNOWN_CALORIE)){
                // If the meal is not in the dishDB and does not have a calorie tag,
                // the dish will be added to the dishDB with a default calorie of 700
                // and added to the meal log with a default value of 700 as well
                Dish mealNonNegativeCal = new Dish(wantToAdd.getName(), new Calorie(700));
                wantToAdd = mealNonNegativeCal;
                model.addDish(mealNonNegativeCal);
                Meal toAddMeal = new Meal(mealNonNegativeCal, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);
            } else {
                model.addDish(wantToAdd);
                Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);
            }
        }

        //model.addDish(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, wantToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
