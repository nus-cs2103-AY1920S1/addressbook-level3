package seedu.guilttrip.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.guilttrip.storage.JsonAdaptedCategory.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.guilttrip.storage.JsonAdaptedCategory.WRONG_CATEGORY_TYPE_MESSAGE_FORMAT;
import static seedu.guilttrip.testutil.Assert.assertThrows;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_SHOPPING;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Category;

public class JsonAdaptedCategoryTest {

    private static final String INVALID_CATEGORY_NAME_BLANK = "";
    private static final String INVALID_CATEGORY_TYPE_BLANK = "";
    private static final String INVALID_CATEGORY_TYPE_BUDGET = "Budget";

    private static final String VALID_CATEGORY_NAME = CATEGORY_SHOPPING.getCategoryName().toString();
    private static final String VALID_CATEGORY_TYPE = CATEGORY_SHOPPING.getCategoryType().toString();

    @Test
    public void toModelType_validCategoryDetails_returnsExpense() throws Exception {
        JsonAdaptedCategory category = new JsonAdaptedCategory(CATEGORY_SHOPPING);
        assertEquals(CATEGORY_SHOPPING, category.toModelType());
    }

    @Test
    public void toModelType_invalidValues_throwsIllegalArgumentExceptionAndIllegalValueExeption() {
        //blank Name
        JsonAdaptedCategory expenseWithBlankCategoryName =
                new JsonAdaptedCategory(INVALID_CATEGORY_NAME_BLANK, VALID_CATEGORY_TYPE);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS_NAME_NOT_EMPTY;
        assertThrows(IllegalArgumentException.class, expectedMessage, expenseWithBlankCategoryName::toModelType);

        //blank Type
        JsonAdaptedCategory expenseWithBlankCategoryType =
                new JsonAdaptedCategory(VALID_CATEGORY_NAME, INVALID_CATEGORY_TYPE_BLANK);
        expectedMessage = String.format(WRONG_CATEGORY_TYPE_MESSAGE_FORMAT, VALID_CATEGORY_NAME);
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithBlankCategoryType::toModelType);

        //Wrong Type
        JsonAdaptedCategory expenseWithWrongType =
                new JsonAdaptedCategory(VALID_CATEGORY_NAME, INVALID_CATEGORY_TYPE_BUDGET);
        expectedMessage = String.format(WRONG_CATEGORY_TYPE_MESSAGE_FORMAT, VALID_CATEGORY_NAME);
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithWrongType::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedCategory expenseWithNullType =
                new JsonAdaptedCategory(VALID_CATEGORY_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "category Type");
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithNullType::toModelType);

        JsonAdaptedCategory expenseWithNullName =
                new JsonAdaptedCategory(null, VALID_CATEGORY_TYPE);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "category Name");
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithNullName::toModelType);
    }
}
