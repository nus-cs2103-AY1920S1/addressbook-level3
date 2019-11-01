package cs.f10.t1.nursetraverse.model.datetime;

import static cs.f10.t1.nursetraverse.model.datetime.DateTimeTest.INVALID_INPUT;
import static cs.f10.t1.nursetraverse.model.datetime.DateTimeTest.VALID_INPUT_1200;
import static cs.f10.t1.nursetraverse.model.datetime.DateTimeTest.VALID_INPUT_1300;
import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class EndDateTimeTest {

    public static final EndDateTime VALID_END_DATE_TIME = new EndDateTime(new Date());

    @Test
    public void constructorString_null_nullPointerException() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> new DateTime(nullString));
    }

    @Test
    public void constructorDate_null_nullPointerException() {
        Date nullDate = null;
        assertThrows(NullPointerException.class, () -> new DateTime(nullDate));
    }

    @Test
    public void constructor_valid_noException() {
        assertDoesNotThrow(() -> new EndDateTime(VALID_INPUT_1300));
        assertDoesNotThrow(() -> new EndDateTime(new Date()));
    }

    @Test
    public void isValidEndDateTime() {
        assertTrue(() -> EndDateTime.isValidEndDateTime(VALID_INPUT_1200, VALID_INPUT_1300));
        assertTrue(() -> EndDateTime.isValidEndDateTime(VALID_INPUT_1300, VALID_INPUT_1300));
        assertFalse(() -> EndDateTime.isValidEndDateTime(VALID_INPUT_1300, VALID_INPUT_1200));
        assertFalse(() -> EndDateTime.isValidEndDateTime(INVALID_INPUT, VALID_INPUT_1200));
        assertFalse(() -> EndDateTime.isValidEndDateTime(INVALID_INPUT, INVALID_INPUT));
        assertFalse(() -> EndDateTime.isValidEndDateTime(VALID_INPUT_1200, INVALID_INPUT));
    }
}
