package dukecooks.testutil.mealplan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.model.util.SampleMealPlanDataUtil;

/**
 * A utility class to help with building MealPlan objects.
 */
public class MealPlanBuilder {

    public static final String DEFAULT_NAME = "Omelette Week";
    public static final String DEFAULT_RECIPENAME = "Cheese Omelette";

    private MealPlanName name;
    private List<RecipeName> day1;

    public MealPlanBuilder() {
        name = new MealPlanName(DEFAULT_NAME);
        calories = new Calories(DEFAULT_CALORIES);
        carbs = new Carbs(DEFAULT_CARBS);
        fats = new Fats(DEFAULT_FATS);
        protein = new Protein(DEFAULT_PROTEIN);
        ingredients = new HashSet<>();
        ingredients.add(new Ingredient(DEFAULT_INGREDIENT));
    }

    /**
     * Initializes the MealPlanBuilder with the data of {@code mealPlanToCopy}.
     */
    public MealPlanBuilder(MealPlan mealPlanToCopy) {
        name = mealPlanToCopy.getName();
        ingredients = new HashSet<>(mealPlanToCopy.());
    }

    /**
     * Sets the {@code MealPlanName} of the {@code MealPlan} that we are building.
     */
    public MealPlanBuilder withName(String name) {
        this.name = new MealPlanName(name);
        return this;
    }

    /**
     * Parses the {@code ingredients} into a {@code Set<Ingredient>} and set it
     * to the {@code MealPlan} that we are building.
     */
    public MealPlanBuilder withIngredients(String ... ingredients) {
        this.ingredients = SampleMealPlanDataUtil.getIngredientSet(ingredients);
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code MealPlan} that we are building.
     */
    public MealPlanBuilder withCalories(String calories) {
        this.calories = new Calories(calories);
        return this;
    }

    /**
     * Sets the {@code Carbs} of the {@code MealPlan} that we are building.
     */
    public MealPlanBuilder withCarbs(String carbs) {
        this.carbs = new Carbs(carbs);
        return this;
    }

    /**
     * Sets the {@code Fats} of the {@code MealPlan} that we are building.
     */
    public MealPlanBuilder withFats(String fats) {
        this.fats = new Fats(fats);
        return this;
    }

    /**
     * Sets the {@code Protein} of the {@code MealPlan} that we are building.
     */
    public MealPlanBuilder withProtein(String protein) {
        this.protein = new Protein(protein);
        return this;
    }

    public MealPlan build() {
        return new MealPlan(name, ingredients, calories, carbs, fats, protein);
    }

}
