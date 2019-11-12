package io.xpire.storage;

import static io.xpire.storage.JsonAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.COOKIE;
import static io.xpire.testutil.TypicalItemsFields.INVALID_NAME;
import static io.xpire.testutil.TypicalItemsFields.INVALID_TAG;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_COOKIE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_COCOA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.model.item.Name;
import io.xpire.model.tag.Tag;

public class JsonAdaptedItemTest {

    private static final List<JsonAdaptedTag> VALID_TAGS = new ArrayList<>() {{
            add(new JsonAdaptedTag(new Tag(VALID_TAG_COCOA)));
        }};

    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        JsonAdaptedItem item = new JsonAdaptedItem(COOKIE);
        assertEquals(COOKIE, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(INVALID_NAME, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedItem item =
                new JsonAdaptedItem(VALID_NAME_COOKIE, invalidTags);
        assertThrows(IllegalValueException.class, item::toModelType);
    }

}
