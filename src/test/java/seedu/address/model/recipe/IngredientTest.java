package seedu.address.model.recipe;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.recipe.components.Ingredient;

public class IngredientTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Ingredient(null));
    }

    @Test
    public void constructor_invalidIngredientName_throwsIllegalArgumentException() {
        String invalidIngredientName = "";
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(invalidIngredientName));
    }

    @Test
    public void isValidIngredientName() {
        // null ingredient name
        assertThrows(NullPointerException.class, () -> Ingredient.isValidIngredientName(null));
    }

}
