package seedu.eatme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eatme.storage.JsonAdaptedEatery.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalEateries.TEXAS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.exceptions.IllegalValueException;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Name;

public class JsonAdaptedEateryTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ISOPEN = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_CATEGORY = "_Ch1nese";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = TEXAS.getName().toString();
    private static final String VALID_ISOPEN = String.valueOf(TEXAS.getIsOpen());
    private static final String VALID_ADDRESS = TEXAS.getAddress().toString();
    private static final String VALID_CATEGORY = TEXAS.getCategory().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TEXAS.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEateryDetails_returnsEatery() throws Exception {
        JsonAdaptedEatery eatery = new JsonAdaptedEatery(TEXAS);
        assertEquals(TEXAS, eatery.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEatery eatery =
                new JsonAdaptedEatery(INVALID_NAME, VALID_ISOPEN, VALID_ADDRESS, VALID_CATEGORY, null,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEatery eatery = new JsonAdaptedEatery(null, VALID_ISOPEN, VALID_ADDRESS, VALID_CATEGORY,
                null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_invalidIsOpen_throwsIllegalValueException() {
        JsonAdaptedEatery eatery =
                new JsonAdaptedEatery(VALID_NAME, INVALID_ISOPEN, VALID_ADDRESS, VALID_CATEGORY,
                        null, VALID_TAGS);
        String expectedMessage = "isOpen has to be either true or false, not blank or anything else.";
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_nullIsOpen_throwsIllegalValueException() {
        JsonAdaptedEatery eatery = new JsonAdaptedEatery(VALID_NAME, null, VALID_ADDRESS, VALID_CATEGORY,
                null, VALID_TAGS);
        String expectedMessage = "Eatery's isOpen field is missing!";
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedEatery eatery =
                new JsonAdaptedEatery(VALID_NAME, VALID_ISOPEN, INVALID_ADDRESS, VALID_CATEGORY,
                        null, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedEatery eatery = new JsonAdaptedEatery(VALID_NAME, VALID_ISOPEN, null, VALID_CATEGORY,
                null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedEatery eatery =
                new JsonAdaptedEatery(VALID_NAME, VALID_ISOPEN, VALID_ADDRESS, INVALID_CATEGORY,
                        null, VALID_TAGS);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedEatery eatery = new JsonAdaptedEatery(VALID_NAME, VALID_ISOPEN, VALID_ADDRESS,
                null, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, eatery::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedEatery eatery =
                new JsonAdaptedEatery(VALID_NAME, VALID_ISOPEN, VALID_ADDRESS, VALID_CATEGORY,
                        null, invalidTags);
        assertThrows(IllegalValueException.class, eatery::toModelType);
    }

}
