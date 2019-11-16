package seedu.ifridge.storage.grocerylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.storage.JsonAdaptedGroceryItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalGroceryItems.BANANA;
import static seedu.ifridge.testutil.TypicalGroceryItems.SPAGHETTI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.storage.JsonAdaptedGroceryItem;
import seedu.ifridge.storage.JsonAdaptedTag;

public class JsonAdaptedGroceryItemTest {
    private static final String INVALID_NAME = "M@ngo";
    private static final String INVALID_TAG = "#fruit";

    private static final String VALID_NAME = SPAGHETTI.getName().toString();
    private static final String VALID_AMOUNT = SPAGHETTI.getAmount().toString();
    private static final String VALID_EXPIRY_DATE = SPAGHETTI.getExpiryDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = SPAGHETTI.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validGroceryItemDetails_returnsGroceryItem() throws Exception {
        JsonAdaptedGroceryItem groceryItem = new JsonAdaptedGroceryItem(BANANA);
        assertEquals(BANANA, groceryItem.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGroceryItem groceryItem =
                new JsonAdaptedGroceryItem(INVALID_NAME, VALID_AMOUNT, VALID_EXPIRY_DATE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, groceryItem::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGroceryItem groceryItem = new JsonAdaptedGroceryItem(
                null, VALID_AMOUNT, VALID_EXPIRY_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, groceryItem::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedGroceryItem groceryItem =
                new JsonAdaptedGroceryItem(VALID_NAME, VALID_AMOUNT, VALID_EXPIRY_DATE, invalidTags);
        assertThrows(IllegalValueException.class, groceryItem::toModelType);
    }

}
