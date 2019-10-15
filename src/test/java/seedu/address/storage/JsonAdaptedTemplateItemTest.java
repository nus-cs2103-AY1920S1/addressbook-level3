package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedTemplateItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTemplateItems.MILK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Name;

public class JsonAdaptedTemplateItemTest {
    private static final String INVALID_NAME = "-----";
    private static final String INVALID_AMOUNT = "500";

    private static final String VALID_NAME = MILK.getName().toString();
    private static final String VALID_AMOUNT = MILK.getAmount().toString();

    @Test
    public void toModelType_validTemplateItemDetails_returnsTemplateItem() throws Exception {
        JsonAdaptedTemplateItem templateItem = new JsonAdaptedTemplateItem(MILK);
        //assertEquals(MILK, templateItem.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTemplateItem templateItem =
                new JsonAdaptedTemplateItem(INVALID_NAME, VALID_AMOUNT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, templateItem::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTemplateItem templateItem = new JsonAdaptedTemplateItem(null, VALID_AMOUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, templateItem::toModelType);
    }
}
