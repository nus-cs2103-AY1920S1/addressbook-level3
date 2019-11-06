package seedu.address.model.incident;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IncidentDateTimeTest {

    private static final String VALID_DATETIME = "2016-12-20T14:30:40";

    @Test
    public void constructorValidDateTimeSuccess() {
        IncidentDateTime validDateTime = new IncidentDateTime("2016-12-20T14:30:40");
        assertEquals(VALID_DATETIME, validDateTime.toString());
    }

    @Test
    public void isValidIncidentDateTime() {
        // invalid dateTime
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("")); // empty string
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("   ")); // spaces only
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("hello")); // alphabets only
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("h1e2l3l4o5")); // alphanumeric
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("12345678")); // all numbers but wrong format

        // wrong number of digits for day
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-64T14:30:40")); // more than 31
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-00T14:30:40")); // 0
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-6T14:30:40")); // 1 digit
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-642T14:30:40")); // more than 2 digits

        // wrong number of digits for hour
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T35:30:40")); // more than 24
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T1:30:40")); // 1 digit
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T143:30:40")); // more than 2 digits

        // wrong number of digits for minute
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:300:40")); // more than 2 digits
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:65:40")); // more than 60
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:3:40")); // 1 digit

        // wrong number of digits for second
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:400")); // more than 2 digits
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:75")); // more than 60
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:4")); // 1 digit

        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("20161220T14:30:40")); // no hyphens
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T143040")); // no colons
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-2014:30:40")); // no T
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20S14:30:40")); // wrong letter

        // valid dateTime
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:40")); // valid date time
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("2016-01-04T11:00:00")); // valid date time
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("2029-12-04T11:00:00")); // future date time
    }
}
