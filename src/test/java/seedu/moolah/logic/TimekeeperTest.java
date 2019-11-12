package seedu.moolah.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class TimekeeperTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timekeeper(null));
    }

    @Test
    public void convertToLocalDateTime() {
        Date date = new Date();
        LocalDateTime expectedDateTime = LocalDateTime.now().withNano(0);
        LocalDateTime actualDateTime = Timekeeper.convertToLocalDateTime(date);
        assertEquals(expectedDateTime, actualDateTime);
    }
}
