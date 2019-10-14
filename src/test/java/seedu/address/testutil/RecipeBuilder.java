package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.recipe.Calories;
import seedu.address.model.recipe.Carbs;
import seedu.address.model.recipe.Fats;
import seedu.address.model.recipe.Ingredient;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Protein;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Omelette";
    public static final String DEFAULT_INGREDIENT = "Eggs";
    public static final String DEFAULT_CALORIES = "154";
    public static final String DEFAULT_CARBS = "1";
    public static final String DEFAULT_FATS = "12";
    public static final String DEFAULT_PROTEIN = "11";

    private Name name;
    private Set<Ingredient> ingredients;
    private Calories calories;
    private Carbs carbs;
    private Fats fats;
    private Protein protein;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        calories = new Calories(DEFAULT_CALORIES);
        carbs = new Carbs(DEFAULT_CARBS);
        fats = new Fats(DEFAULT_FATS);
        protein = new Protein(DEFAULT_PROTEIN);
        ingredients = new HashSet<>();
        ingredients.add(new Ingredient(DEFAULT_INGREDIENT));
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        calories = recipeToCopy.getCalories();
        carbs = recipeToCopy.getCarbs();
        fats = recipeToCopy.getFats();
        protein = recipeToCopy.getProtein();
        ingredients = new HashSet<>(recipeToCopy.getIngredients());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code ingredients} into a {@code Set<Ingredient>} and set it
     * to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withIngredients(String ... tags) {
        this.ingredients = SampleDataUtil.getIngredientSet(tags);
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withCalories(String calories) {
        this.calories = new Calories(calories);
        return this;
    }

    /**
     * Sets the {@code Carbs} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withCarbs(String carbs) {
        this.carbs = new Carbs(carbs);
        return this;
    }

    /**
     * Sets the {@code Fats} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withFats(String fats) {
        this.fats = new Fats(fats);
        return this;
    }

    /**
     * Sets the {@code Protein} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withProtein(String protein) {
        this.protein = new Protein(protein);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, ingredients, calories, carbs, fats, protein);
    }

}
