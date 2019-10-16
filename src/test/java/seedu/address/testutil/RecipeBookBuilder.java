package seedu.address.testutil;

import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class to help with building RecipeBook objects.
 * Example usage: <br>
 *     {@code RecipeBook dc = new RecipeBookBuilder().withRecipe("Fried", "Chicken").build();}
 */
public class RecipeBookBuilder {

    private RecipeBook recipeBook;

    public RecipeBookBuilder() {
        recipeBook = new RecipeBook();
    }

    public RecipeBookBuilder(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    /**
     * Adds a new {@code Recipe} to the {@code RecipeBook} that we are building.
     */
    public RecipeBookBuilder withRecipe(Recipe recipe) {
        recipeBook.addRecipe(recipe);
        return this;
    }

    public RecipeBook build() {
        return recipeBook;
    }
}
