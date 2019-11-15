package dukecooks.testutil.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe MILO = new RecipeBuilder().withName("Milo")
            .withIngredients("Milo Powder")
            .withCalories("180").withCarbs("0").withFats("0").withProtein("0").build();
    public static final Recipe OMELETTE = new RecipeBuilder().withName("Cheese Omelette")
            .withIngredients("Eggs", "Cheese")
            .withCalories("358").withCarbs("1").withFats("28").withProtein("21").build();
    public static final Recipe TUNA = new RecipeBuilder().withName("Tuna Sandwich")
            .withIngredients("Tuna", "Bread")
            .withCalories("290").withCarbs("29").withFats("10").withProtein("24").build();
    public static final Recipe MAGGI = new RecipeBuilder().withName("Chicken Maggi")
            .withIngredients("Chicken Maggi")
            .withCalories("402").withCarbs("59").withFats("15").withProtein("10").build();
    public static final Recipe CHICKEN = new RecipeBuilder().withName("Fried Chicken")
            .withIngredients("Chicken Wings", "Flour", "Oil")
            .withCalories("410").withCarbs("3").withFats("29").withProtein("34").build();
    public static final Recipe TEA = new RecipeBuilder().withName("Tea")
            .withIngredients("Tea Leaves")
            .withCalories("0").withCarbs("0").withFats("0").withProtein("0").build();
    public static final Recipe FRIES = new RecipeBuilder().withName("French Fries")
            .withIngredients("Potatoes", "Salt", "Oil")
            .withCalories("312").withCarbs("41").withFats("15").withProtein("3").build();

    // Manually added
    public static final Recipe TAMAGO = new RecipeBuilder().withName("Tamago Maki")
            .withIngredients("Eggs", "Rice", "Nori")
            .withCalories("300").withCarbs("22").withFats("15").withProtein("20").build();
    public static final Recipe KAPPA = new RecipeBuilder().withName("Kappa Maki")
            .withIngredients("Cucumber", "Rice", "Nori")
            .withCalories("130").withCarbs("35").withFats("0").withProtein("0").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe FISH = new RecipeBuilder().withName(CommandTestUtil.VALID_NAME_FISH)
            .withIngredients(CommandTestUtil.VALID_INGREDIENT_FISH)
            .withCalories(CommandTestUtil.VALID_CALORIES_FISH).withCarbs(CommandTestUtil.VALID_CARBS_FISH)
            .withFats(CommandTestUtil.VALID_FATS_FISH).withProtein(CommandTestUtil.VALID_PROTEIN_FISH)
            .build();;
    public static final Recipe BURGER = new RecipeBuilder().withName(CommandTestUtil.VALID_NAME_BURGER)
            .withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER)
            .withCalories(CommandTestUtil.VALID_CALORIES_BURGER).withCarbs(CommandTestUtil.VALID_CARBS_BURGER)
            .withFats(CommandTestUtil.VALID_FATS_BURGER).withProtein(CommandTestUtil.VALID_PROTEIN_BURGER)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Maki"; // A keyword that matches MAKI

    private TypicalRecipes() {} // prevents instantiation

    /**
     * Returns an {@code RecipeBook} with all the typical recipes.
     */
    public static RecipeBook getTypicalRecipeBook() {
        RecipeBook dc = new RecipeBook();
        for (Recipe recipe : getTypicalRecipes()) {
            dc.addRecipe(recipe);
        }
        return dc;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(MILO, OMELETTE, TUNA, MAGGI, CHICKEN, TEA, FRIES));
    }
}
