package dukecooks.model.recipe;

import dukecooks.model.recipe.components.Recipe;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Duke Cooks
 */
public interface ReadOnlyRecipeBook {

    /**
     * Returns an unmodifiable view of the recipes list.
     * This list will not contain any duplicate recipes.
     */
    ObservableList<Recipe> getRecipeList();

}
