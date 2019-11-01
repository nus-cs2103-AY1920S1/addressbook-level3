package calofit.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import calofit.testutil.TypicalMeals;


public class JsonAdaptedMealTest {
    private static final String INVALID_TIMESTAMP = "2019";

    private static final JsonAdaptedDish VALID_DISH = new JsonAdaptedDish(TypicalMeals.MUSHROOM_SOUP.getDish());

    /*
    @Test
    public void toModelType_validMealDetails_returnsMeal() throws Exception {
        JsonAdaptedMeal meal = new JsonAdaptedMeal(TypicalMeals.MUSHROOM_SOUP);
        assertEquals(TypicalMeals.MUSHROOM_SOUP, meal.toModelType());
    }
    */

    @Test
    public void toModelType_invalidTimeStamp_throwsIllegalValueException() {
        JsonAdaptedMeal meal =
                new JsonAdaptedMeal(VALID_DISH, INVALID_TIMESTAMP);
        assertThrows(DateTimeParseException.class, meal::toModelType);
    }

}
