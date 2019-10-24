package dukecooks.storage.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static dukecooks.testutil.Assert.assertThrows;
import static dukecooks.testutil.recipe.TypicalRecipes.OMELETTE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.common.Name;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Protein;
import dukecooks.testutil.Assert;
import org.junit.jupiter.api.Test;

public class JsonAdaptedRecipeTest {
    private static final String INVALID_NAME = "@melette";
    private static final String INVALID_INGREDIENT = "#eggs";
    private static final String INVALID_CALORIES = "1a";
    private static final String INVALID_CARBS = "1a";
    private static final String INVALID_FATS = "1a";
    private static final String INVALID_PROTEIN = "1a";

    private static final String VALID_NAME = OMELETTE.getName().toString();
    private static final List<JsonAdaptedIngredient> VALID_INGREDIENTS = OMELETTE.getIngredients().stream()
            .map(JsonAdaptedIngredient::new)
            .collect(Collectors.toList());
    private static final String VALID_CALORIES = OMELETTE.getCalories().value;
    private static final String VALID_CARBS = OMELETTE.getCarbs().value;
    private static final String VALID_FATS = OMELETTE.getFats().value;
    private static final String VALID_PROTEIN = OMELETTE.getProtein().value;

    @Test
    public void toModelType_validRecipeDetails_returnsRecipe() throws Exception {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(OMELETTE);
        assertEquals(OMELETTE, recipe.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(INVALID_NAME, VALID_INGREDIENTS,
                        VALID_CALORIES, VALID_CARBS, VALID_FATS, VALID_PROTEIN);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(null, VALID_INGREDIENTS,
                VALID_CALORIES, VALID_CARBS, VALID_FATS, VALID_PROTEIN);
        String expectedMessage = String.format(JsonAdaptedRecipe.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidIngredients_throwsIllegalValueException() {
        List<JsonAdaptedIngredient> invalidIngredients = new ArrayList<>(VALID_INGREDIENTS);
        invalidIngredients.add(new JsonAdaptedIngredient(INVALID_INGREDIENT));
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, invalidIngredients,
                        VALID_CALORIES, VALID_CARBS, VALID_FATS, VALID_PROTEIN);
        Assert.assertThrows(IllegalValueException.class, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidCalories_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_INGREDIENTS,
                        INVALID_CALORIES, VALID_CARBS, VALID_FATS, VALID_PROTEIN);
        String expectedMessage = Calories.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidCarbs_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_INGREDIENTS,
                        VALID_CALORIES, INVALID_CARBS, VALID_FATS, VALID_PROTEIN);
        String expectedMessage = Carbs.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidFats_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_INGREDIENTS,
                        VALID_CALORIES, VALID_CARBS, INVALID_FATS, VALID_PROTEIN);
        String expectedMessage = Fats.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidProtein_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_INGREDIENTS,
                        VALID_CALORIES, VALID_CARBS, VALID_FATS, INVALID_PROTEIN);
        String expectedMessage = Protein.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

}
