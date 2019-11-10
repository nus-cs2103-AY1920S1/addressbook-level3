package calofit.logic.commands;

import static calofit.logic.parser.CliSyntax.PREFIX_CALORIES;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import calofit.commons.core.Messages;
import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.exceptions.DuplicateDishException;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.tag.Tag;
import calofit.model.util.Timestamp;


/**
 * Adds a dish to the dish database.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meals to the meal list below. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_CALORIES + "CALORIES] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "OR \n"
            + "INDEX (must be a positive integer between 1 and 2 billion that is in the suggested meal list) \n"
            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_NAME + "Carbonara "
            + PREFIX_CALORIES + "300 "
            + PREFIX_TAG + "salty \n"
            + "OR \n"
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "New meal added to meal log: %1$s";
    public static final String MESSAGE_DUPLICATE_MEAL = "This dish already exists in the dish database";
    public static final String MESSAGE_MEAL_NOT_IN_DATABASE = "This dish is not in our database. "
            + "Please update the calories using the c/ "
            + "calories set to 700 by default";

    public static final int DEFAULT_MEAL_CALORIE = 700;

    private Dish toAdd;
    private int dishNumber;
    private boolean isNumber = false;
    private List<Integer> dishIntList;
    private boolean isList = false;

    /**
     * Creates an AddCommand to add the specified {@code Dish}
     */
    public AddCommand(Dish dish) {
        requireNonNull(dish);
        toAdd = dish;
    }

    public AddCommand() {}

    public AddCommand(int dishNumber) {
        requireNonNull(dishNumber);
        this.dishNumber = dishNumber;
        this.isNumber = true;
    }

    public AddCommand(List<Integer> dishIntList) {
        requireNonNull(dishIntList);
        this.dishIntList = dishIntList;
        this.isList = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MealLog mealLog = model.getMealLog();

        if (isList) {
            String addedDishesToString = "";
            LinkedList<Meal> toBeAddedMealList = new LinkedList<Meal>();
            for (int i = 0; i < dishIntList.size(); i++) {
                int dishInt = dishIntList.get(i);
                if (dishInt <= 0 || dishInt > model.getFilteredDishList().size()) {
                    throw new CommandException(String.format(Messages.MESSAGE_INVALID_MEAL_INDEX, dishInt));
                } else {
                    Dish wantToAdd = model.getFilteredDishList().get(dishInt - 1);
                    Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                    toBeAddedMealList.add(toAddMeal);
                    //mealLog.addMeal(toAddMeal);
                    addedDishesToString = addedDishesToString + "\n" + (i + 1) + ". " + wantToAdd.toString();

                }
            }

            mealLog.addListOfMeals(toBeAddedMealList);

            return new CommandResult(String.format(MESSAGE_SUCCESS, addedDishesToString));

        } else {
            Dish wantToAdd = toAdd;

            if (model.hasDish(wantToAdd)) {
                Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                mealLog.addMeal(toAddMeal);

                return new CommandResult(String.format(MESSAGE_SUCCESS, wantToAdd));
            } else {
                if (model.hasDishName(wantToAdd.getName())
                        && !wantToAdd.getCalories().equals(Calorie.UNKNOWN_CALORIE)) {
                    //case where the dishname is in the dishDB and the calorie tag is used
                    model.addDish(wantToAdd);
                    Meal toAddMeal = new Meal(wantToAdd, new Timestamp(LocalDateTime.now()));
                    mealLog.addMeal(toAddMeal);

                } else if (model.hasDishName(wantToAdd.getName())
                        && wantToAdd.getCalories().equals(Calorie.UNKNOWN_CALORIE)) {
                    // Case where the dish name is in the dishDB and the calorie tag is not used
                    Set<Tag> newTag = wantToAdd.getTags();
                    wantToAdd = model.getDishByName(toAdd.getName());
                    try {
                        model.addDish(wantToAdd);
                    } catch (DuplicateDishException e) {
                        System.out.println("There is a duplicate dish");
                    }
                    Set<Tag> combineNewAndOldTags = new HashSet<Tag>();
                    combineNewAndOldTags.addAll(wantToAdd.getTags());
                    combineNewAndOldTags.addAll(newTag);
                    Dish newDish = new Dish(wantToAdd.getName(), wantToAdd.getCalories(), combineNewAndOldTags);
                    wantToAdd = newDish;
                    Meal toAddMeal = new Meal(newDish, new Timestamp(LocalDateTime.now()));
                    mealLog.addMeal(toAddMeal);
                } else if (!model.hasDishName(wantToAdd.getName())
                        && wantToAdd.getCalories().equals(Calorie.UNKNOWN_CALORIE)) {
                    // If the meal is not in the dishDB and does not have a calorie tag,
                    // the dish will be added to the dishDB with a default calorie of 700
                    // and added to the meal log with a default value of 700 as well
                    Dish mealNonNegativeCal = new Dish(wantToAdd.getName(), new Calorie(DEFAULT_MEAL_CALORIE),
                            wantToAdd.getTags());
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
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
