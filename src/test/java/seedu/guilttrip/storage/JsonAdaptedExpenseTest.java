package seedu.guilttrip.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.guilttrip.storage.JsonAdaptedExpense.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.guilttrip.testutil.Assert.assertThrows;
import static seedu.guilttrip.testutil.TypicalEntries.TRAVEL_EXPENSE;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;

public class JsonAdaptedExpenseTest {

    private static final String INVALID_UNIQUE_ID = "";
    private static final String INVALID_CATEGORY = "";
    private static final String INVALID_AMOUNT_NEGATIVE = "-0.98";
    private static final String INVALID_AMOUNT_3_DP = "1.999";
    private static final String INVALID_DATE_DAYS_EXCEEDED = "2019-02-31";
    private static final String INVALID_DATE_WRONG_FORMAT = "2019/02-02";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_UNIQUE_ID = UUID.randomUUID().toString();
    private static final String VALID_DESCRIPTION = TRAVEL_EXPENSE.getDesc().toString();
    private static final String VALID_CATEGORY = TRAVEL_EXPENSE.getCategory().toString();
    private static final String VALID_DATE = TRAVEL_EXPENSE.getDate().toString();
    private static final String VALID_AMOUNT = TRAVEL_EXPENSE.getAmount().toString();
    private static final List<JsonAdaptedTag> VALID_TAG = TRAVEL_EXPENSE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(TRAVEL_EXPENSE);
        assertEquals(TRAVEL_EXPENSE, expense.toModelType());
    }

    @Test
    public void toModelType_invalidNegativeAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expenseWithInvalidNegativeAmount =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, VALID_CATEGORY, VALID_DESCRIPTION, INVALID_AMOUNT_NEGATIVE,
                        VALID_DATE, VALID_TAG);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithInvalidNegativeAmount::toModelType);
    }

    @Test
    public void toModelType_invalidDecimalAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expenseWithInvalidDecimalPlaces =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, VALID_CATEGORY, VALID_DESCRIPTION,
                        INVALID_AMOUNT_3_DP, VALID_DATE, VALID_TAG);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithInvalidDecimalPlaces::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonAdaptedExpense expenseWithNullName =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, VALID_CATEGORY, null, VALID_AMOUNT,
                        VALID_DATE, VALID_TAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithNullName::toModelType);
    }

    @Test
    public void toModelType_invalidDateDaysExceeded_throwsIllegalArgumentException() {
        JsonAdaptedExpense expenseWithInvalidDate =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, VALID_CATEGORY, VALID_DESCRIPTION, VALID_AMOUNT,
                        INVALID_DATE_DAYS_EXCEEDED, VALID_TAG);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS_FOR_ENTRIES;
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithInvalidDate::toModelType);
    }

    @Test
    public void toModelType_invalidDateWrongFormat_throwsIllegalArgumentException() {
        JsonAdaptedExpense expenseWithInvalidDate =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, VALID_CATEGORY, VALID_DESCRIPTION, VALID_AMOUNT,
                        INVALID_DATE_WRONG_FORMAT, VALID_TAG);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS_FOR_ENTRIES;
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithInvalidDate::toModelType);
    }

    @Test
    public void toModelType_nullCategoryName_throwsIllegalValueException() {
        JsonAdaptedExpense expenseWithNullCategoryName =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, null, VALID_DESCRIPTION, VALID_AMOUNT,
                        VALID_DATE, VALID_TAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expenseWithNullCategoryName::toModelType);
    }

    @Test
    public void toModelType_invalidCategoryName_throwsIllegalValueException() {
        JsonAdaptedExpense expenseWithNullCategoryName =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, INVALID_CATEGORY, VALID_DESCRIPTION, VALID_AMOUNT,
                        VALID_DATE, VALID_TAG);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS_NAME_NOT_EMPTY;
        assertThrows(IllegalArgumentException.class, expectedMessage, expenseWithNullCategoryName::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAG);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedExpense expenseWithInvalidTags =
                new JsonAdaptedExpense(VALID_UNIQUE_ID, VALID_CATEGORY, VALID_DESCRIPTION, VALID_AMOUNT,
                        VALID_DATE, invalidTags);
        assertThrows(IllegalValueException.class, expenseWithInvalidTags::toModelType);
    }
}
