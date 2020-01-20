package seedu.address.model.project;

import org.junit.jupiter.api.Test;
import java.text.ParseException;


import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

public class TimeTest {

    @Test
    public void constructor_successful() throws ParseException {
        Time time = new Time("05/05/2019 1600");
    }

    @Test
    public void constructor_successfulButInvalid() throws ParseException {
        Time time = new Time("05/05/2019 2400");
    }


    @Test
    public void constructor_invalidDateFormat() {
        assertThrows(ParseException.class, () -> new Time("05-05-2019 1600"));
    }

    @Test
    public void constructor_invalidTimeFormat() {
        assertThrows(ParseException.class, () -> new Time("05/05/2019 4pm"));
    }

    @Test
    public void isValidTimeAndDate_valid_returnsTrue() throws ParseException {
        Time time = new Time("00/00/0000 0000");
        assertTrue(Time.isValidTimeAndDate(time.getTime()));
    }

    @Test
    public void isValidTimeAndDate_invalid_returnsFalse() throws ParseException {
        Time time = new Time("12/12/19 1500");
        assertFalse(Time.isValidTimeAndDate(time.getTime()));
    }

    @Test
    public void isValidTime_valid_returnsTrue() {
        String time = "0000";
        assertTrue(Time.isValidTime(time));
    }

    @Test
    public void isValidTime_invalid_returnsFalse() {
        String time = "2400";
        assertFalse(Time.isValidTime(time));
    }

    @Test
    public void isValidDate_valid_returnsTrue() {
        String date = "12/12/2019";
        assertTrue(Time.isValidDate(date));
    }

    @Test
    public void isValidDate_invalidYear_returnsFalse() {
        String date = "12/12/1899";
        assertFalse(Time.isValidDate(date));
    }

    @Test
    public void isValidDate_nonLeapYear_returnsFalse() {
        String date = "29/02/2019";
        assertFalse(Time.isValidDate(date));
    }

    @Test
    public void isValidDate_leapYear_returnsTrue() {
        String date = "29/02/2016";
        assertTrue(Time.isValidDate(date));
    }
}

