package dukecooks.storage.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.OMELETTE_MP;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.testutil.Assert;

public class JsonAdaptedMealPlanTest {
    private static final String INVALID_NAME = "@melette Wee|<";
    private static final String INVALID_DAY1 = "#@melette";
    private static final String INVALID_DAY2 = "#@melette";
    private static final String INVALID_DAY3 = "#@melette";
    private static final String INVALID_DAY4 = "#@melette";
    private static final String INVALID_DAY5 = "#@melette";
    private static final String INVALID_DAY6 = "#@melette";
    private static final String INVALID_DAY7 = "#@melette";

    private static final String VALID_NAME = OMELETTE_MP.getName().toString();
    private static final List<JsonAdaptedRecipeName> VALID_DAY1 = OMELETTE_MP.getDay1().stream()
            .map(JsonAdaptedRecipeName::new).collect(Collectors.toList());
    private static final List<JsonAdaptedRecipeName> VALID_DAY2 = OMELETTE_MP.getDay2().stream()
            .map(JsonAdaptedRecipeName::new).collect(Collectors.toList());
    private static final List<JsonAdaptedRecipeName> VALID_DAY3 = OMELETTE_MP.getDay3().stream()
            .map(JsonAdaptedRecipeName::new).collect(Collectors.toList());
    private static final List<JsonAdaptedRecipeName> VALID_DAY4 = OMELETTE_MP.getDay4().stream()
            .map(JsonAdaptedRecipeName::new).collect(Collectors.toList());
    private static final List<JsonAdaptedRecipeName> VALID_DAY5 = OMELETTE_MP.getDay5().stream()
            .map(JsonAdaptedRecipeName::new).collect(Collectors.toList());
    private static final List<JsonAdaptedRecipeName> VALID_DAY6 = OMELETTE_MP.getDay6().stream()
            .map(JsonAdaptedRecipeName::new).collect(Collectors.toList());
    private static final List<JsonAdaptedRecipeName> VALID_DAY7 = OMELETTE_MP.getDay7().stream()
            .map(JsonAdaptedRecipeName::new).collect(Collectors.toList());

    @Test
    public void toModelType_validMealPlanDetails_returnsMealPlan() throws Exception {
        JsonAdaptedMealPlan recipe = new JsonAdaptedMealPlan(OMELETTE_MP);
        assertEquals(OMELETTE_MP, recipe.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(INVALID_NAME, VALID_DAY1, VALID_DAY2, VALID_DAY3, VALID_DAY4, VALID_DAY5,
                        VALID_DAY6, VALID_DAY7);
        String expectedMessage = MealPlanName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, mealPlan::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMealPlan mealPlan = new JsonAdaptedMealPlan(null, VALID_DAY1, VALID_DAY2, VALID_DAY3, VALID_DAY4,
                VALID_DAY5, VALID_DAY6, VALID_DAY7);
        String expectedMessage = String.format(JsonAdaptedMealPlan.MISSING_FIELD_MESSAGE_FORMAT,
                MealPlanName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, mealPlan::toModelType);
    }

    @Test
    public void toModelType_invalidDay1RecipeNames_throwsIllegalValueException() {
        List<JsonAdaptedRecipeName> invalidRecipeNames = new ArrayList<>(VALID_DAY1);
        invalidRecipeNames.add(new JsonAdaptedRecipeName(INVALID_DAY1));
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(VALID_NAME, invalidRecipeNames, VALID_DAY2, VALID_DAY3, VALID_DAY4, VALID_DAY5,
                        VALID_DAY6, VALID_DAY7);
        Assert.assertThrows(IllegalValueException.class, mealPlan::toModelType);
    }

    @Test
    public void toModelType_invalidDay2RecipeNames_throwsIllegalValueException() {
        List<JsonAdaptedRecipeName> invalidRecipeNames = new ArrayList<>(VALID_DAY2);
        invalidRecipeNames.add(new JsonAdaptedRecipeName(INVALID_DAY2));
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(VALID_NAME, VALID_DAY1, invalidRecipeNames, VALID_DAY3, VALID_DAY4, VALID_DAY5,
                        VALID_DAY6, VALID_DAY7);
        Assert.assertThrows(IllegalValueException.class, mealPlan::toModelType);
    }

    @Test
    public void toModelType_invalidDay3RecipeNames_throwsIllegalValueException() {
        List<JsonAdaptedRecipeName> invalidRecipeNames = new ArrayList<>(VALID_DAY3);
        invalidRecipeNames.add(new JsonAdaptedRecipeName(INVALID_DAY3));
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(VALID_NAME, VALID_DAY1, VALID_DAY2, invalidRecipeNames, VALID_DAY4, VALID_DAY5,
                        VALID_DAY6, VALID_DAY7);
        Assert.assertThrows(IllegalValueException.class, mealPlan::toModelType);
    }

    @Test
    public void toModelType_invalidDay4RecipeNames_throwsIllegalValueException() {
        List<JsonAdaptedRecipeName> invalidRecipeNames = new ArrayList<>(VALID_DAY4);
        invalidRecipeNames.add(new JsonAdaptedRecipeName(INVALID_DAY4));
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(VALID_NAME, VALID_DAY1, VALID_DAY2, VALID_DAY3, invalidRecipeNames, VALID_DAY5,
                        VALID_DAY6, VALID_DAY7);
        Assert.assertThrows(IllegalValueException.class, mealPlan::toModelType);
    }

    @Test
    public void toModelType_invalidDay5RecipeNames_throwsIllegalValueException() {
        List<JsonAdaptedRecipeName> invalidRecipeNames = new ArrayList<>(VALID_DAY5);
        invalidRecipeNames.add(new JsonAdaptedRecipeName(INVALID_DAY5));
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(VALID_NAME, VALID_DAY1, VALID_DAY2, VALID_DAY3, VALID_DAY4, invalidRecipeNames,
                        VALID_DAY6, VALID_DAY7);
        Assert.assertThrows(IllegalValueException.class, mealPlan::toModelType);
    }

    @Test
    public void toModelType_invalidDay6RecipeNames_throwsIllegalValueException() {
        List<JsonAdaptedRecipeName> invalidRecipeNames = new ArrayList<>(VALID_DAY6);
        invalidRecipeNames.add(new JsonAdaptedRecipeName(INVALID_DAY6));
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(VALID_NAME, VALID_DAY1, VALID_DAY2, VALID_DAY3, VALID_DAY4, VALID_DAY5,
                        invalidRecipeNames, VALID_DAY7);
        Assert.assertThrows(IllegalValueException.class, mealPlan::toModelType);
    }

    @Test
    public void toModelType_invalidDay7RecipeNames_throwsIllegalValueException() {
        List<JsonAdaptedRecipeName> invalidRecipeNames = new ArrayList<>(VALID_DAY7);
        invalidRecipeNames.add(new JsonAdaptedRecipeName(INVALID_DAY7));
        JsonAdaptedMealPlan mealPlan =
                new JsonAdaptedMealPlan(VALID_NAME, VALID_DAY1, VALID_DAY2, VALID_DAY3, VALID_DAY4, VALID_DAY5,
                        VALID_DAY6, invalidRecipeNames);
        Assert.assertThrows(IllegalValueException.class, mealPlan::toModelType);
    }

}
