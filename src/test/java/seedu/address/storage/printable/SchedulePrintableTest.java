package seedu.address.storage.printable;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SchedulePrintableTest {

    private static final String VALID_FILENAME = "SampleSchedule.png";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SchedulePrintable(null, VALID_FILENAME));
    }
}
