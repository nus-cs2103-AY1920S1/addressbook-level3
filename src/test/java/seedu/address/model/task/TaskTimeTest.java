package seedu.address.model.task;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskTime(null));
    }

    @Test
    public void constructor_invalidTaskTime_throwsIllegalArgumentException() {
        String invalidTaskTime = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskTime(invalidTaskTime));
    }
}

