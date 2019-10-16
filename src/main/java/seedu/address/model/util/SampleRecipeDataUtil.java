package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
import seedu.address.model.common.Name;
import seedu.address.model.recipe.Calories;
import seedu.address.model.recipe.Carbs;
import seedu.address.model.recipe.Fats;
import seedu.address.model.recipe.Ingredient;
import seedu.address.model.recipe.Protein;
import seedu.address.model.recipe.Recipe;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleRecipeDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Tea"),
                getIngredientSet("Tea Leaves"),
                    new Calories("0"), new Carbs("0"), new Fats("0"), new Protein("0")),
            new Recipe(new Name("Milo"),
                getIngredientSet("Milo Powder"),
                new Calories("180"), new Carbs("29"), new Fats("5"), new Protein("5")),
            new Recipe(new Name("Cheese Omelette"),
                getIngredientSet("Eggs", "Cheese"),
                    new Calories("358"), new Carbs("1"), new Fats("28"), new Protein("21")),
            new Recipe(new Name("Tuna Sandwich"),
                getIngredientSet("Tuna", "Bread", "Mayonnaise"),
                    new Calories("290"), new Carbs("29"), new Fats("10"), new Protein("24")),
            new Recipe(new Name("Chicken Maggi"),
                getIngredientSet("Chicken Maggi"),
                    new Calories("402"), new Carbs("59"), new Fats("15"), new Protein("10")),
            new Recipe(new Name("Fried Chicken"),
                getIngredientSet("Chicken Wings", "Flour", "Oil"),
                    new Calories("410"), new Carbs("3"), new Fats("29"), new Protein("34"))
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleDc = new RecipeBook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleDc.addRecipe(sampleRecipe);
        }
        return sampleDc;
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
