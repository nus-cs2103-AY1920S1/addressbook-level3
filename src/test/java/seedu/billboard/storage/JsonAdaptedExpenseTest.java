package seedu.billboard.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.storage.JsonAdaptedExpense.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.FOOD;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Name;


public class JsonAdaptedExpenseTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_AMOUNT = "2af2";
    private static final String INVALID_DATE = "^ba#Fj0";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = FOOD.getName().toString();
    private static final String VALID_DESCRIPTION = FOOD.getDescription().toString();
    private static final String VALID_AMOUNT = FOOD.getAmount().toString();
    private static final String VALID_DATE = FOOD.getCreated().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = FOOD.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_ARCHIVE = "";

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(FOOD);
        assertEquals(FOOD, expense.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(INVALID_NAME, VALID_DESCRIPTION, VALID_AMOUNT,
                        VALID_DATE, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(
                null, VALID_DESCRIPTION, VALID_AMOUNT, VALID_DATE, VALID_TAGS, VALID_ARCHIVE);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, null, VALID_AMOUNT,
                VALID_DATE, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);

    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, INVALID_AMOUNT,
                        VALID_DATE, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, null,
                VALID_DATE, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);

    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, VALID_AMOUNT,
                        INVALID_DATE, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = CreatedDateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, VALID_AMOUNT,
                        null, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CreatedDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);

    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, VALID_AMOUNT,
                        VALID_DATE, invalidTags, VALID_ARCHIVE);

        assertThrows(IllegalValueException.class, expense::toModelType);
    }

}
