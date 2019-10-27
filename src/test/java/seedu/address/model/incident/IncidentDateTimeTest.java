package seedu.address.model.incident;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class IncidentDateTimeTest {

    private static final String VALID_DATETIME = "Dec 20, 2016, 2:30:40 PM";

    @Test
    public void toString_validDateTime_success() {
        IncidentDateTime validDateTime = new IncidentDateTime("Dec 20, 2016, 2:30:40 PM");
        assertEquals(VALID_DATETIME, validDateTime.toString());
    }

    @Test
    public void isValidIncidentDateTime() {

        // null incident dateTime
        Assert.assertThrows(NullPointerException.class, () -> IncidentDateTime.isValidIncidentDateTimeFormat(null));

        // invalid dateTime
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("")); // empty string
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("   ")); // spaces only
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("hello")); // alphabets only
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("h1e2l3l4o5")); // alphanumeric
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("12345678")); // all numbers but wrong format
        // wrong letters for month
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Why 02, 2019, 12:00:00 PM"));
        // wrong number of digits for day
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun 64, 2019, 1:00:00 PM"));
        // wrong number of digits for hour
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun 04, 2019, 189:00:00 PM"));
        // wrong number of digits for minute
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun 04, 2019, 1:009:00 PM"));
        // wrong number of digits for second
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun 04, 2019, 1:00:008 PM"));
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun4,2019,1:00:00PM")); // no spaces
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun 04 2019 1:00:00 PM")); // no commas
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun 04 2019 1:00:00")); // no AM or PM
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("Jun 04 2019 17:00:00 PM")); // 24h time format


        // valid dateTime
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("Dec 20, 2016, 2:30:40 PM")); // valid date time
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("Jan 4, 2019, 11:00:00 AM")); // valid date time
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("Dec 04, 2029, 11:00:00 AM")); // future date time
    }
}
