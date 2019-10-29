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
                    new Calories("410"), new Carbs("3"), new Fats("29"), new Protein("34"))
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
