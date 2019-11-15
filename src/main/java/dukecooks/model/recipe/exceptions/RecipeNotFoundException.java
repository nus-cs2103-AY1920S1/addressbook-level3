package dukecooks.model.recipe.exceptions;

/**
 * Signals that the operation is unable to find the specified recipe.
 */
public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException() {
        super();
    }

    public RecipeNotFoundException(String message) {
        super(message);
    }
}
