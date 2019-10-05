package io.xpire.storage;

import static io.xpire.storage.JsonAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.KIWI;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;

public class JsonAdaptedItemTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EXPIRY_DATE = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = KIWI.getName().toString();
    private static final String VALID_EXPIRY_DATE = KIWI.getExpiryDate().toString();
    private static final String VALID_REMINDER_THRESHOLD = "0";
    private static final List<JsonAdaptedTag> VALID_TAGS = KIWI.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validItemDetails_returnsPerson() throws Exception {
        JsonAdaptedItem item = new JsonAdaptedItem(KIWI);
        assertEquals(KIWI, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedItem item =
                new JsonAdaptedItem(INVALID_NAME, VALID_EXPIRY_DATE, VALID_REMINDER_THRESHOLD, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(null, VALID_EXPIRY_DATE, VALID_REMINDER_THRESHOLD, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }


    @Test
    public void toModelType_invalidExpiryDate_throwsIllegalValueException() {
        JsonAdaptedItem item =
                new JsonAdaptedItem(VALID_NAME, INVALID_EXPIRY_DATE, VALID_REMINDER_THRESHOLD, VALID_TAGS);
        String expectedMessage = ExpiryDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullExpiryDate_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME, null, VALID_REMINDER_THRESHOLD, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ExpiryDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedItem item =
                new JsonAdaptedItem(VALID_NAME, VALID_EXPIRY_DATE, VALID_REMINDER_THRESHOLD, invalidTags);
        assertThrows(IllegalValueException.class, item::toModelType);
    }
}
