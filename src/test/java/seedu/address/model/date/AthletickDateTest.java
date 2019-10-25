package seedu.address.model.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AthletickDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AthletickDate(null));
    }

    @Test
    public void constructor_invalidTrainingDate_throwsParseException() {
        String invalidTrainingDate = "";
        assertThrows(ParseException.class, () -> new AthletickDate(invalidTrainingDate));
    }

    @Test
    public void constructor_validTrainingDate_success() throws ParseException {
        String validTrainingDate = "01012000";
        AthletickDate td = new AthletickDate(validTrainingDate);
        assertTrue(true);
    }

    @Test
    public void getDay() throws ParseException {
        AthletickDate td = new AthletickDate("01012000");
        int day = td.getDay();
        assertEquals(1, day);
    }

    @Test
    public void getMonth() throws ParseException {
        AthletickDate td = new AthletickDate("01012000");
        int month = td.getMonth();
        assertEquals(1, month);
    }

    @Test
    public void getYear() throws ParseException {
        AthletickDate td = new AthletickDate("01012000");
        int year = td.getYear();
        assertEquals(2000, year);
    }
}
