package seedu.moneygowhere.storage;

import static seedu.moneygowhere.storage.JsonAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.reminder.ReminderMessage;
import seedu.moneygowhere.model.spending.Date;

class JsonAdaptedReminderTest {

    private static final String INVALID_DATE = " ";
    private static final String VALID_DATE = "today";
    private static final String VALID_REMINDER_MESSAGE = "Pay Monthly school fees";

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(INVALID_DATE, VALID_REMINDER_MESSAGE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(null, VALID_REMINDER_MESSAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedReminder reminder =
                new JsonAdaptedReminder(VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ReminderMessage.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

}
