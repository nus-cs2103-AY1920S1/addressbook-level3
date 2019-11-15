package dukecooks.storage.mealplan;

import static dukecooks.logic.commands.CommandTestUtil.VALID_INGREDIENT_FISH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.testutil.Assert;

public class JsonAdaptedRecipeNameTest {
    private static final String INVALID_NAME = "D0ry F!sh";

    private static final String VALID_NAME = VALID_INGREDIENT_FISH;

    @Test
    public void toModelType_validRecipeNameDetails_returnsRecipeName() throws Exception {
        JsonAdaptedRecipeName recipeName = new JsonAdaptedRecipeName(VALID_INGREDIENT_FISH);
        assertEquals(new RecipeName(VALID_INGREDIENT_FISH), recipeName.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRecipeName recipeName =
                new JsonAdaptedRecipeName(INVALID_NAME);
        String expectedMessage = RecipeName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipeName::toModelType);
    }

}
