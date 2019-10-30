package dukecooks.storage.recipe;

import static dukecooks.logic.commands.CommandTestUtil.VALID_INGREDIENT_FISH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.testutil.Assert;

public class JsonAdaptedIngredientTest {
    private static final String INVALID_NAME = "D0ry F!sh";

    private static final String VALID_NAME = VALID_INGREDIENT_FISH;

    @Test
    public void toModelType_validIngredientDetails_returnsIngredient() throws Exception {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_INGREDIENT_FISH);
        assertEquals(new Ingredient(VALID_INGREDIENT_FISH), ingredient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(INVALID_NAME);
        String expectedMessage = Ingredient.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

}
