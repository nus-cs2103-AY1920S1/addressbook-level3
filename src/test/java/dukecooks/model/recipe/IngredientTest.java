package dukecooks.model.recipe;

import static dukecooks.testutil.Assert.assertThrows;

import dukecooks.model.recipe.components.Ingredient;
import dukecooks.testutil.Assert;
import org.junit.jupiter.api.Test;

public class IngredientTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Ingredient(null));
    }

    @Test
    public void constructor_invalidIngredientName_throwsIllegalArgumentException() {
        String invalidIngredientName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Ingredient(invalidIngredientName));
    }

    @Test
    public void isValidIngredientName() {
        // null ingredient name
        Assert.assertThrows(NullPointerException.class, () -> Ingredient.isValidIngredientName(null));
    }

}
