package dukecooks.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleRecipeDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new RecipeName("Tea"),
                getIngredientSet("Tea Leaves"),
                    new Calories("0"), new Carbs("0"), new Fats("0"), new Protein("0")),
            new Recipe(new RecipeName("Milo"),
                getIngredientSet("Milo Powder"),
                new Calories("180"), new Carbs("29"), new Fats("5"), new Protein("5")),
            new Recipe(new RecipeName("Cheese Omelette"),
                getIngredientSet("Eggs", "Cheese"),
                    new Calories("358"), new Carbs("1"), new Fats("28"), new Protein("21")),
            new Recipe(new RecipeName("Tuna Sandwich"),
                getIngredientSet("Tuna", "Bread", "Mayonnaise"),
                    new Calories("290"), new Carbs("29"), new Fats("10"), new Protein("24")),
            new Recipe(new RecipeName("Chicken Maggi"),
                getIngredientSet("Chicken Maggi"),
                    new Calories("402"), new Carbs("59"), new Fats("15"), new Protein("10")),
            new Recipe(new RecipeName("Fried Chicken"),
                getIngredientSet("Chicken Wings", "Flour", "Oil"),
                    new Calories("410"), new Carbs("3"), new Fats("29"), new Protein("34")),
            new Recipe(new RecipeName("Chicken Salad"),
                    getIngredientSet("Chicken", "Lettuce", "Tomato", "Croutons"),
                    new Calories("254"), new Carbs("4"), new Fats("18"), new Protein("19")),
            new Recipe(new RecipeName("Beef Lasagna"),
                    getIngredientSet("Ground Beef", "Tomatoes", "Pasta"),
                    new Calories("310"), new Carbs("33"), new Fats("12"), new Protein("17")),
            new Recipe(new RecipeName("Carbonara"),
                    getIngredientSet("Pasta", "Eggs", "Bacon"),
                    new Calories("308"), new Carbs("46"), new Fats("7"), new Protein("19")),
            new Recipe(new RecipeName("Egg Fried Rice"),
                    getIngredientSet("Eggs", "Rice", "Mixed Vegetables"),
                    new Calories("238"), new Carbs("45"), new Fats("4"), new Protein("6")),
            new Recipe(new RecipeName("Century Egg Porridge"),
                    getIngredientSet("Rice", "Century Egg", "Minced Pork"),
                    new Calories("256"), new Carbs("39"), new Fats("5"), new Protein("10")),
            new Recipe(new RecipeName("Bangers and Mash"),
                    getIngredientSet("Sausages", "Potatoes", "Gravy"),
                    new Calories("843"), new Carbs("61"), new Fats("32"), new Protein("40")),
            new Recipe(new RecipeName("Prawn Aglio Olio"),
                    getIngredientSet("Prawn", "Pasta", "Garlic", "Chilli Flakes"),
                    new Calories("486"), new Carbs("66"), new Fats("16"), new Protein("18")),
            new Recipe(new RecipeName("Tonkotsu Ramen"),
                    getIngredientSet("Ramen Noodles", "Char Siew", "Pork Broth", "Nori"),
                    new Calories("656"), new Carbs("62"), new Fats("22"), new Protein("49")),
            new Recipe(new RecipeName("Basil Pork Rice"),
                    getIngredientSet("Rice", "Minced Pork", "Basil"),
                    new Calories("590"), new Carbs("67"), new Fats("16"), new Protein("42"))
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleRb = new RecipeBook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleRb.addRecipe(sampleRecipe);
        }
        return sampleRb;
    }

    /**
     * Returns a ingredient set containing the list of strings given.
     */
    public static Set<Ingredient> getIngredientSet(String... strings) {
        return Arrays.stream(strings)
                .map(Ingredient::new)
                .collect(Collectors.toSet());
    }

}
