package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.storage.JsonAdaptedFood.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.TONKATSU_RAMEN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;

public class JsonAdaptedFoodTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRICE = "+651234";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_CATEGORY = "#&*";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_LOCATION = "";
    private static final String INVALID_OPENING_HOURS = "hours";
    private static final String INVALID_RESTRICTIONS = "";

    private static final String VALID_NAME = TONKATSU_RAMEN.getName().toString();
    private static final String VALID_PRICE = TONKATSU_RAMEN.getPrice().toString();
    private static final String VALID_DESCRIPTION = TONKATSU_RAMEN.getDescription().toString(); // last updated here
    private static final String VALID_CATEGORY = TONKATSU_RAMEN.getCategory().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TONKATSU_RAMEN.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_LOCATION = "The Deck @ NUS";
    private static final String VALID_OPENING_HOURS = "0800 1800";
    private static final String VALID_RESTRICTIONS = "Not halal";

    @Test
    public void toModelType_validfoodDetails_returnsfood() throws Exception {
        JsonAdaptedFood food = new JsonAdaptedFood(TONKATSU_RAMEN);
        assertEquals(TONKATSU_RAMEN, food.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(INVALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                        VALID_CATEGORY, VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(null, VALID_PRICE, VALID_DESCRIPTION,
                VALID_CATEGORY, VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, INVALID_PRICE, VALID_DESCRIPTION,
                        VALID_CATEGORY, VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, null, VALID_DESCRIPTION,
                VALID_CATEGORY, VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, INVALID_DESCRIPTION,
                        VALID_CATEGORY, VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_PRICE, null,
                VALID_CATEGORY, VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                        INVALID_CATEGORY, VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidOpeningHours_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                        VALID_CATEGORY,
                        VALID_TAGS, VALID_LOCATION, INVALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = OpeningHours.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullOpeningHours_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                VALID_CATEGORY,
                VALID_TAGS, VALID_LOCATION, null, VALID_RESTRICTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OpeningHours.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidRestrictions_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                        VALID_CATEGORY,
                        VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, INVALID_RESTRICTIONS);
        String expectedMessage = Restrictions.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullRestrictions_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                VALID_CATEGORY,
                VALID_TAGS, VALID_LOCATION, VALID_OPENING_HOURS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Restrictions.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                        VALID_CATEGORY, invalidTags, VALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        assertThrows(IllegalValueException.class, food::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedFood food =
                new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                        VALID_CATEGORY, VALID_TAGS, INVALID_LOCATION, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedFood food = new JsonAdaptedFood(VALID_NAME, VALID_PRICE, VALID_DESCRIPTION,
                VALID_CATEGORY, VALID_TAGS, null, VALID_OPENING_HOURS, VALID_RESTRICTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }
}
