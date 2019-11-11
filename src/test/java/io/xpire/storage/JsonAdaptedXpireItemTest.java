package io.xpire.storage;

import static io.xpire.storage.JsonAdaptedXpireItem.MISSING_FIELD_MESSAGE_FORMAT;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.FISH;
import static io.xpire.testutil.TypicalItemsFields.INVALID_EXPIRY_DATE;
import static io.xpire.testutil.TypicalItemsFields.INVALID_EXPIRY_DATE_LOWER;
import static io.xpire.testutil.TypicalItemsFields.INVALID_EXPIRY_DATE_UPPER;
import static io.xpire.testutil.TypicalItemsFields.INVALID_NAME;
import static io.xpire.testutil.TypicalItemsFields.INVALID_QUANTITY_INTEGER;
import static io.xpire.testutil.TypicalItemsFields.INVALID_REMINDER_THRESHOLD;
import static io.xpire.testutil.TypicalItemsFields.INVALID_REMINDER_THRESHOLD_RANGE;
import static io.xpire.testutil.TypicalItemsFields.INVALID_TAG;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRIDGE;
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

public class JsonAdaptedXpireItemTest {

    private static final List<JsonAdaptedTag> VALID_TAGS = new ArrayList<>() {{
            add(new JsonAdaptedTag(new Tag(VALID_TAG_FRIDGE)));
        }};

    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        JsonAdaptedXpireItem item = new JsonAdaptedXpireItem(FISH);
        assertEquals(FISH, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedXpireItem item =
                new JsonAdaptedXpireItem(INVALID_NAME, VALID_EXPIRY_DATE_FISH, VALID_QUANTITY_FISH,
                        VALID_REMINDER_THRESHOLD_FISH, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedXpireItem item = new JsonAdaptedXpireItem(null, VALID_EXPIRY_DATE_FISH, VALID_QUANTITY_FISH,
                VALID_REMINDER_THRESHOLD_FISH, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }


    @Test
    public void toModelType_invalidExpiryDate_throwsIllegalValueException() {
        JsonAdaptedXpireItem item = new JsonAdaptedXpireItem(VALID_NAME_FISH, INVALID_EXPIRY_DATE,
                VALID_QUANTITY_FISH, VALID_REMINDER_THRESHOLD_FISH, VALID_TAGS);
        String expectedMessage = ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidExpiryDateUpper_throwsIllegalValueException() {
        JsonAdaptedXpireItem item = new JsonAdaptedXpireItem(VALID_NAME_FISH, INVALID_EXPIRY_DATE_UPPER,
                VALID_QUANTITY_FISH, VALID_REMINDER_THRESHOLD_FISH, VALID_TAGS);
        String expectedMessage = ExpiryDate.MESSAGE_CONSTRAINTS_UPPER;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidExpiryDateLower_throwsIllegalValueException() {
        JsonAdaptedXpireItem item = new JsonAdaptedXpireItem(VALID_NAME_FISH, INVALID_EXPIRY_DATE_LOWER,
                VALID_QUANTITY_FISH, VALID_REMINDER_THRESHOLD_FISH, VALID_TAGS);
        String expectedMessage = ExpiryDate.MESSAGE_CONSTRAINTS_OUTDATED;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullExpiryDate_throwsIllegalValueException() {
        JsonAdaptedXpireItem item = new JsonAdaptedXpireItem(VALID_NAME_FISH, null, VALID_QUANTITY_FISH,
                VALID_REMINDER_THRESHOLD_FISH, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ExpiryDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedXpireItem item =
                new JsonAdaptedXpireItem(VALID_NAME_FISH, VALID_EXPIRY_DATE_FISH, VALID_QUANTITY_FISH,
                        VALID_REMINDER_THRESHOLD_FISH, invalidTags);
        assertThrows(IllegalValueException.class, item::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedXpireItem item =
                new JsonAdaptedXpireItem(VALID_NAME_APPLE, VALID_EXPIRY_DATE_APPLE, INVALID_QUANTITY_INTEGER,
                        VALID_REMINDER_THRESHOLD_APPLE, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidReminderThreshold_throwsIllegalValueException() {
        JsonAdaptedXpireItem item =
                new JsonAdaptedXpireItem(VALID_NAME_FISH, VALID_EXPIRY_DATE_FISH, VALID_QUANTITY_FISH,
                        INVALID_REMINDER_THRESHOLD, VALID_TAGS);
        String expectedMessage = ReminderThreshold.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidReminderThresholdRange_throwsIllegalValueException() {
        JsonAdaptedXpireItem item =
                new JsonAdaptedXpireItem(VALID_NAME_FISH, VALID_EXPIRY_DATE_FISH, VALID_QUANTITY_FISH,
                        INVALID_REMINDER_THRESHOLD_RANGE, VALID_TAGS);
        String expectedMessage = ReminderThreshold.MESSAGE_CONSTRAINTS_LOWER;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }
}
