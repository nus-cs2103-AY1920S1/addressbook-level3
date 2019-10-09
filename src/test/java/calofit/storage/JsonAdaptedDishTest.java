package calofit.storage;

import static calofit.storage.JsonAdaptedDish.MISSING_FIELD_MESSAGE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.dish.Name;
import calofit.testutil.Assert;
import calofit.testutil.TypicalDishes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class JsonAdaptedDishTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = TypicalDishes.BENSON.getName().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalDishes.BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validDishDetails_returnsDish() throws Exception {
        JsonAdaptedDish person = new JsonAdaptedDish(TypicalDishes.BENSON);
        assertEquals(TypicalDishes.BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDish person =
                new JsonAdaptedDish(INVALID_NAME, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDish person = new JsonAdaptedDish(null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedDish person =
                new JsonAdaptedDish(VALID_NAME, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
