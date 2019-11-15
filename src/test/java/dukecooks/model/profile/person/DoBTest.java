package dukecooks.model.profile.person;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DoBTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DoB(null));
    }

    @Test
    public void constructor_invalidDoB_throwsIllegalArgumentException() {
        String invalidDoB = "";
        assertThrows(IllegalArgumentException.class, () -> new DoB(invalidDoB));
    }

    @Test
    void isValidDate() {
        // invalid DoB
        assertFalse(DoB.isValidDate("")); // empty string
        assertFalse(DoB.isValidDate(" ")); // spaces only
        assertFalse(DoB.isValidDate("29/2/2019")); // non leap year february month
        assertFalse(DoB.isValidDate("31/11/2019")); // november only has 30 days
        assertFalse(DoB.isValidDate("2/14/2019")); // no such month

        // valid DoB
        assertTrue(DoB.isValidDate("28/2/2019")); // last day of february
        assertTrue(DoB.isValidDate("30/11/2019")); // last day of november
        assertTrue(DoB.isValidDate("1/1/2019")); // first day of january
    }

    @Test
    void isValidDatePeriod() {
        // valid date before present
        assertTrue(DoB.isValidDatePeriod("01/10/2019"));

        // invalid date from future
        assertFalse(DoB.isValidDatePeriod("12/12/2030"));
    }

    @Test
    void testDoBToString() {
        DoB doB = new DoB("30/09/1997");
        String expected = "30/09/1997";
        assertEquals(doB.toString(), expected);
    }

}
