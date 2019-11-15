package dukecooks.model.recipe.exceptions;

/**
 * Signals that the operation is unable to find the specified ingredient.
 */
public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
