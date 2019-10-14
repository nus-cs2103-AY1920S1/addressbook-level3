package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedFood.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroceryItems.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Name;

public class JsonAdaptedFoodTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_AMOUNT = BENSON.getAmount().toString();
    private static final String VALID_EXPIRY_DATE = BENSON.getExpiryDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    /*@Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedFood person = new JsonAdaptedFood(BENSON);
        assertEquals(BENSON, person.toModelType());
    }*/

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedFood person =
                new JsonAdaptedFood(INVALID_NAME, VALID_AMOUNT, VALID_EXPIRY_DATE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFood person = new JsonAdaptedFood(null, VALID_AMOUNT, VALID_EXPIRY_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFood person =
                new JsonAdaptedFood(VALID_NAME, VALID_AMOUNT, VALID_EXPIRY_DATE, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
