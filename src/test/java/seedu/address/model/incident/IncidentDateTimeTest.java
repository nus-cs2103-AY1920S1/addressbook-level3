package seedu.address.model.incident;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

//@@author atharvjoshi
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

        // non date time related input partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("")); // empty string
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("   ")); // spaces only
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("hello")); // alphabets only
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("h1e2l3l4o5")); // alphanumeric
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("12345678")); // all numbers

        // wrong input for year partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("-12-16T14:30:40")); // empty year
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("201-12-16T14:30:40")); // less than 4 digits
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("20165-12-16T14:30:40")); // more than 4 digits

        // wrong input for month partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016--20T14:30:40")); // empty month
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-1-20T14:30:40")); // less than 2 digits
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-122-20T14:30:40")); // more than 2 digits
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-15-20T14:30:40")); // more than 12
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-00-20T14:30:40")); // 0

        // wrong input for day partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-T14:30:40")); // empty day
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-64T14:30:40")); // more than 31
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-00T14:30:40")); // 0
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-6T14:30:40")); // 1 digit
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-642T14:30:40")); // more than 2 digits

        // wrong input for hour partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-16T:30:40")); // empty hour
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T35:30:40")); // more than 24
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T1:30:40")); // 1 digit
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T143:30:40")); // more than 2 digits

        // wrong input for minute partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-16T14::40")); // empty minute
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:300:40")); // more than 2 digits
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:65:40")); // more than 60
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:3:40")); // 1 digit

        // wrong input for second partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-16T14:30:")); // empty second
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:400")); // more than 2 digits
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:75")); // more than 60
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:4")); // 1 digit

        // wrong input for non-number fields partition
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("20161220T14:30:40")); // no hyphens
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T143040")); // no colons
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016/12/20T14:30:40")); // slashes not dashes
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-2014:30:40")); // no T
        assertFalse(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20S14:30:40")); // wrong letter

        // valid dateTime partition
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("2016-12-20T14:30:40")); // valid date time pm
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("2016-01-04T11:00:00")); // valid date time am
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("2029-12-04T11:00:00")); // future date time
        assertTrue(IncidentDateTime.isValidIncidentDateTimeFormat("1009-12-04T11:00:00")); // past date time
    }
}
