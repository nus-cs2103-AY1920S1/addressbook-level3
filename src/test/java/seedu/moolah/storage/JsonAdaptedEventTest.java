package seedu.moolah.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.BRIAN_BDAY;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.general.Price;
import seedu.moolah.model.general.Timestamp;

public class JsonAdaptedEventTest {
    private static final String INVALID_DESCRIPTION = "R@chel";
    private static final String INVALID_PRICE = "+651234";
    private static final String INVALID_CATEGORY = "#friend";
    private static final String INVALID_TIMESTAMP = "asdf";
    private static final String INVALID_BUDGET_NAME = "sch@@l";

    private static final String VALID_DESCRIPTION = BRIAN_BDAY.getDescription().toString();
    private static final String VALID_PRICE = BRIAN_BDAY.getPrice().toString();
    private static final String VALID_CATEGORY = BRIAN_BDAY.getCategory().getCategoryName();
    private static final String VALID_TIMESTAMP = BRIAN_BDAY.getTimestamp().toString();
    private static final String VALID_BUDGET_NAME = BRIAN_BDAY.getBudgetName().toString();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(BRIAN_BDAY);
        assertEquals(BRIAN_BDAY, event.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(INVALID_DESCRIPTION, VALID_PRICE, VALID_CATEGORY,
                        VALID_TIMESTAMP, VALID_BUDGET_NAME);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(null, VALID_PRICE, VALID_CATEGORY, VALID_TIMESTAMP,
                        VALID_BUDGET_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, INVALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, null,
                        VALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_PRICE,
                        INVALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_PRICE,
                        null, VALID_TIMESTAMP, VALID_BUDGET_NAME);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidTimestamp_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, INVALID_TIMESTAMP, VALID_BUDGET_NAME);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_GENERAL;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullTimestamp_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, null, VALID_BUDGET_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidBudgetName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, INVALID_BUDGET_NAME);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullBudgetName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Budget Name");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
