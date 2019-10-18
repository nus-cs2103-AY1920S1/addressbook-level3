package seedu.address.model.recipe;

import javafx.collections.ObservableList;
import seedu.address.model.recipe.components.Recipe;

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
