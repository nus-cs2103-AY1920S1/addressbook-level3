package io.xpire.storage;

import static io.xpire.logic.CommandParserItemUtil.INVALID_EXPIRY_DATE;
import static io.xpire.logic.CommandParserItemUtil.INVALID_NAME;
import static io.xpire.logic.CommandParserItemUtil.INVALID_QUANTITY;
import static io.xpire.logic.CommandParserItemUtil.INVALID_REMINDER_THRESHOLD;
import static io.xpire.logic.CommandParserItemUtil.INVALID_TAG;
import static io.xpire.logic.CommandParserItemUtil.VALID_EXPIRY_DATE_JELLY;
import static io.xpire.logic.CommandParserItemUtil.VALID_NAME_JELLY;
import static io.xpire.logic.CommandParserItemUtil.VALID_QUANTITY_JELLY;
import static io.xpire.logic.CommandParserItemUtil.VALID_REMINDER_THRESHOLD_JELLY;
import static io.xpire.logic.CommandParserItemUtil.VALID_TAG_FRIDGE;
import static io.xpire.storage.JsonAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.JELLY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.tag.Tag;

public class JsonAdaptedItemTest {

    private static final List<JsonAdaptedTag> VALID_TAGS = new ArrayList<>() {{
            add(new JsonAdaptedTag(new Tag(VALID_TAG_FRIDGE)));
        }};

    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        JsonAdaptedItem item = new JsonAdaptedItem(JELLY);
        assertEquals(JELLY, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedItem item =
                new JsonAdaptedItem(INVALID_NAME, VALID_EXPIRY_DATE_JELLY, VALID_QUANTITY_JELLY,
                        VALID_REMINDER_THRESHOLD_JELLY, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(null, VALID_EXPIRY_DATE_JELLY, VALID_QUANTITY_JELLY,
                VALID_REMINDER_THRESHOLD_JELLY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }


    @Test
    public void toModelType_invalidExpiryDate_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME_JELLY, INVALID_EXPIRY_DATE, VALID_QUANTITY_JELLY,
                        VALID_REMINDER_THRESHOLD_JELLY, VALID_TAGS);
        String expectedMessage = ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullExpiryDate_throwsIllegalValueException() {
        JsonAdaptedItem item = new JsonAdaptedItem(VALID_NAME_JELLY, null, VALID_QUANTITY_JELLY,
                VALID_REMINDER_THRESHOLD_JELLY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ExpiryDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedItem item =
                new JsonAdaptedItem(VALID_NAME_JELLY, VALID_EXPIRY_DATE_JELLY, VALID_QUANTITY_JELLY,
                        VALID_REMINDER_THRESHOLD_JELLY, invalidTags);
        assertThrows(IllegalValueException.class, item::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedItem item =
                new JsonAdaptedItem(VALID_NAME_JELLY, VALID_EXPIRY_DATE_JELLY, INVALID_QUANTITY,
                        VALID_REMINDER_THRESHOLD_JELLY, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidReminderThreshold_throwsIllegalValueException() {
        JsonAdaptedItem item =
                new JsonAdaptedItem(VALID_NAME_JELLY, VALID_EXPIRY_DATE_JELLY, VALID_QUANTITY_JELLY,
                        INVALID_REMINDER_THRESHOLD, VALID_TAGS);
        String expectedMessage = ReminderThreshold.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }
}
