package seedu.address.model.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AthletickDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AthletickDate(1, 1, 1, 0,
                null));
    }

    @Test
    public void getDay() throws ParseException {
        AthletickDate td = new AthletickDate(1, 1, 2019, 1, "January");
        int day = td.getDay();
        assertEquals(1, day);
    }

    @Test
    public void getMonth() throws ParseException {
        AthletickDate td = new AthletickDate(1, 1, 2019, 1, "January");
        int month = td.getMonth();
        assertEquals(1, month);
    }

    @Test
    public void getYear() throws ParseException {
        AthletickDate td = new AthletickDate(1, 1, 2019, 1, "January");
        int year = td.getYear();
        assertEquals(2019, year);
    }

    @Test
    public void getType() throws ParseException {
        AthletickDate td = new AthletickDate(1, 1, 2019, 1, "January");
        int type = td.getType();
        assertEquals(1, type);
    }

    @Test
    public void getMth() throws ParseException {
        AthletickDate td = new AthletickDate(1, 1, 2019, 1, "January");
        String mth = td.getMth();
        assertEquals("January", mth);
    }
}
