package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.storage.JsonAdaptedFood.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalFood.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;

public class JsonAdaptedFoodTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRICE = "+651234";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PRICE = BENSON.getPrice().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validfoodDetails_returnsfood() throws Exception {
        JsonAdaptedFood food = new JsonAdaptedFood(BENSON);
        assertEquals(BENSON, food.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(INVALID_NAME, VALID_PRICE, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(null, VALID_PRICE, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, INVALID_PRICE, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, null, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, INVALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_PRICE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION, invalidTags);
        assertThrows(IllegalValueException.class, food::toModelType);
    }

}
