package calofit.storage;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.meal.Name;
import calofit.testutil.Assert;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static calofit.storage.JsonAdaptedMeal.MISSING_FIELD_MESSAGE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonAdaptedMealTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = TypicalMeals.BENSON.getName().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalMeals.BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMealDetails_returnsMeal() throws Exception {
        JsonAdaptedMeal person = new JsonAdaptedMeal(TypicalMeals.BENSON);
        assertEquals(TypicalMeals.BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMeal person =
                new JsonAdaptedMeal(INVALID_NAME, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMeal person = new JsonAdaptedMeal(null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedMeal person =
                new JsonAdaptedMeal(VALID_NAME, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
