package seedu.moneygowhere.model.reminder;

import static seedu.moneygowhere.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ReminderMessageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderMessage(null));
    }

}
