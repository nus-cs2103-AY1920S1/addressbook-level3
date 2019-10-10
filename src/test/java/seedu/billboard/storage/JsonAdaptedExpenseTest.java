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
import seedu.billboard.model.expense.*;

public class JsonAdaptedExpenseTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_AMOUNT = "2af2";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = FOOD.getName().toString();
    private static final String VALID_DESCRIPTION = FOOD.getDescription().toString();
    private static final String VALID_AMOUNT = FOOD.getAmount().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = FOOD.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedExpense person = new JsonAdaptedExpense(FOOD);
        assertEquals(FOOD, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedExpense person =
                new JsonAdaptedExpense(INVALID_NAME, VALID_DESCRIPTION, VALID_AMOUNT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedExpense person = new JsonAdaptedExpense(null, VALID_DESCRIPTION, VALID_AMOUNT, VALID_TAGS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, null, VALID_AMOUNT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, INVALID_AMOUNT, VALID_TAGS);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedExpense person =
                new JsonAdaptedExpense(VALID_NAME, VALID_DESCRIPTION, VALID_AMOUNT, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
