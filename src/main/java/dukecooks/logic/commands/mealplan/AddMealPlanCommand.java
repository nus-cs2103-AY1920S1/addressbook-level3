package dukecooks.logic.commands.mealplan;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY1;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY2;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY3;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY4;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY5;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY6;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY7;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Adds a recipe to Duke Cooks.
 */
public class AddMealPlanCommand extends AddCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meal plan to Duke Cooks. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DAY1 + "DAY 1 RECIPES... "
            + PREFIX_DAY2 + "DAY 2 RECIPES... "
            + PREFIX_DAY3 + "DAY 3 RECIPES... "
            + PREFIX_DAY4 + "DAY 4 RECIPES... "
            + PREFIX_DAY5 + "DAY 5 RECIPES... "
            + PREFIX_DAY6 + "DAY 6 RECIPES... "
            + PREFIX_DAY7 + "DAY 7 RECIPES...\n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " "
            + PREFIX_NAME + "Vegetarian Plan"
            + PREFIX_DAY1 + "Broccoli Soup ";

    public static final String MESSAGE_SUCCESS = "New meal plan added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEALPLAN = "This meal plan already exists in Duke Cooks";

    private static final Set<Ingredient> DUMMY_INGREDIENTS = new HashSet<>(Arrays
            .asList(new Ingredient("DUMMY")));
    private static final Calories DUMMY_CALORIES = new Calories("0");
    private static final Carbs DUMMY_CARBS = new Carbs("0");
    private static final Fats DUMMY_FATS = new Fats("0");
    private static final Protein DUMMY_PROTEIN = new Protein("0");

    private static Event event;

    private final MealPlan toAdd;

    private final List<RecipeName> toCheckDay1;
    private final List<RecipeName> toCheckDay2;
    private final List<RecipeName> toCheckDay3;
    private final List<RecipeName> toCheckDay4;
    private final List<RecipeName> toCheckDay5;
    private final List<RecipeName> toCheckDay6;
    private final List<RecipeName> toCheckDay7;

    /**
     * Creates an AddRecipeCommand to add the specified {@code MealPlanBook}
     */
    public AddMealPlanCommand(MealPlan mealPlan, List<RecipeName> day1, List<RecipeName> day2, List<RecipeName> day3,
                              List<RecipeName> day4, List<RecipeName> day5, List<RecipeName> day6,
                              List<RecipeName> day7) {
        requireNonNull(mealPlan);
        toAdd = mealPlan;
        toCheckDay1 = day1;
        toCheckDay2 = day2;
        toCheckDay3 = day3;
        toCheckDay4 = day4;
        toCheckDay5 = day5;
        toCheckDay6 = day6;
        toCheckDay7 = day7;
    }

    public AddMealPlanCommand(MealPlan mealPlan) {
        requireNonNull(mealPlan);
        toAdd = new MealPlan(mealPlan.getName());
        toCheckDay1 = mealPlan.getDay1();
        toCheckDay2 = mealPlan.getDay2();
        toCheckDay3 = mealPlan.getDay3();
        toCheckDay4 = mealPlan.getDay4();
        toCheckDay5 = mealPlan.getDay5();
        toCheckDay6 = mealPlan.getDay6();
        toCheckDay7 = mealPlan.getDay7();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMealPlan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEALPLAN);
        }

        Set<RecipeName> allRecipes = new HashSet<>();
        allRecipes.addAll(toCheckDay1);
        allRecipes.addAll(toCheckDay2);
        allRecipes.addAll(toCheckDay3);
        allRecipes.addAll(toCheckDay4);
        allRecipes.addAll(toCheckDay5);
        allRecipes.addAll(toCheckDay6);
        allRecipes.addAll(toCheckDay7);

        for (RecipeName name : allRecipes) {
            boolean exists = false;
            for (Recipe recipe : model.getRecipeBook().getRecipeList()) {
                if (name.equals(recipe.getName())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                throw new CommandException(String.format(Messages.MESSAGE_RECIPE_DOES_NOT_EXIST, name.fullName));
            }
        }


        List<RecipeName> day1 = new ArrayList<>();
        for (RecipeName recipeName : toCheckDay1) {
            Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                    DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
            if (model.hasRecipe(curr)) {
                day1.add(recipeName);
            }
        }

        List<RecipeName> day2 = new ArrayList<>();
        for (RecipeName recipeName : toCheckDay2) {
            Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                    DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
            if (model.hasRecipe(curr)) {
                day2.add(recipeName);
            }
        }

        List<RecipeName> day3 = new ArrayList<>();
        for (RecipeName recipeName : toCheckDay3) {
            Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                    DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
            if (model.hasRecipe(curr)) {
                day3.add(recipeName);
            }
        }

        List<RecipeName> day4 = new ArrayList<>();
        for (RecipeName recipeName : toCheckDay4) {
            Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                    DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
            if (model.hasRecipe(curr)) {
                day4.add(recipeName);
            }
        }

        List<RecipeName> day5 = new ArrayList<>();
        for (RecipeName recipeName : toCheckDay5) {
            Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                    DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
            if (model.hasRecipe(curr)) {
                day5.add(recipeName);
            }
        }

        List<RecipeName> day6 = new ArrayList<>();
        for (RecipeName recipeName : toCheckDay6) {
            Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                    DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
            if (model.hasRecipe(curr)) {
                day6.add(recipeName);
            }
        }

        List<RecipeName> day7 = new ArrayList<>();
        for (RecipeName recipeName : toCheckDay7) {
            Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                    DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
            if (model.hasRecipe(curr)) {
                day7.add(recipeName);
            }
        }

        MealPlan updatedToAdd = new MealPlan(toAdd.getName(), day1, day2, day3, day4, day5, day6, day7);

        model.addMealPlan(updatedToAdd);

        event = Event.getInstance();
        event.set("mealPlan", "all");

        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMealPlanCommand // instanceof handles nulls
                && toAdd.equals(((AddMealPlanCommand) other).toAdd));
    }
}
