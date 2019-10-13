package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OrganExpiryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OrganExpiryDate(null));
    }

    @Test
    public void constructor_invalidOrganExpiryDate_throwsIllegalArgumentException() {
        String invalidOrganExpiryDate = "";
        assertThrows(IllegalArgumentException.class, () -> new OrganExpiryDate(invalidOrganExpiryDate));
    }

    @Test
    public void isValidExpiryDate() {
        // null organ's expiry date
        assertThrows(NullPointerException.class, () -> OrganExpiryDate.isValidExpiryDate(null));

        // invalid organ's expiry date
        assertFalse(OrganExpiryDate.isValidExpiryDate("")); // empty string
        assertFalse(OrganExpiryDate.isValidExpiryDate(" ")); // spaces only
        assertFalse(OrganExpiryDate.isValidExpiryDate("^")); // only non-alphanumeric characters
        assertFalse(OrganExpiryDate.isValidExpiryDate("23,Jan 2019")); // wrong data format
        assertFalse(OrganExpiryDate.isValidExpiryDate("22/Jan/2019")); // wrong data format
        assertFalse(OrganExpiryDate.isValidExpiryDate("22.01.2019")); // wrong data format
        assertFalse(OrganExpiryDate.isValidExpiryDate("22/01/2019")); // wrong data format
        assertFalse(OrganExpiryDate.isValidExpiryDate("22-01-2019")); // wrong data format
        assertFalse(OrganExpiryDate.isValidExpiryDate("22-Mai-2019")); // wrong spelling
        assertFalse(OrganExpiryDate.isValidExpiryDate("30-Feb-2019")); // day is more than number of days
        assertFalse(OrganExpiryDate.isValidExpiryDate("00-Jan-2019")); // there is no day 00
        assertFalse(OrganExpiryDate.isValidExpiryDate("29-Feb-2019")); // not leap year
        assertFalse(OrganExpiryDate.isValidExpiryDate("23-Janu-2020")); // month not in format
        assertFalse(OrganExpiryDate.isValidExpiryDate("23-Augus-2020")); // month not in format

        // valid organ's expiry date
        assertTrue(OrganExpiryDate.isValidExpiryDate("23-Jan-2020")); // valid date
        assertTrue(OrganExpiryDate.isValidExpiryDate("23-January-2019")); // full month name accepted
        assertTrue(OrganExpiryDate.isValidExpiryDate("23-JaNuArY-2019")); // full month name accepted
        assertTrue(OrganExpiryDate.isValidExpiryDate("29-Feb-2020")); // leap year
        assertTrue(OrganExpiryDate.isValidExpiryDate("29-jan-2020")); // case insensitive
        assertTrue(OrganExpiryDate.isValidExpiryDate("29-JAN-2020")); // case insensitive
    }

    @Test
    public void toStringTest() {
        assertEquals(new OrganExpiryDate("23-Jan-2020").toString(), "23 Jan 2020");
        assertEquals(new OrganExpiryDate("29-JAN-2020").toString(), "29 Jan 2020"); //full month name accepted
        assertEquals(new OrganExpiryDate("29-JANuArY-2020").toString(), "29 Jan 2020"); //case insensitive
    }

    @Test
    public void equals() {
        OrganExpiryDate expiryDate = new OrganExpiryDate("23-Jan-2020");

        // null -> returns false
        assertFalse(expiryDate.equals(null));

        // different organ's expiry date -> return false
        assertFalse(expiryDate.equals(new OrganExpiryDate("24-january-2020")));
        assertFalse(expiryDate.equals(new OrganExpiryDate("23-may-2020")));
        assertFalse(expiryDate.equals(new OrganExpiryDate("23-may-2021")));

        // same object -> returns true
        assertTrue(expiryDate.equals(expiryDate));

        // same organ's expiry date -> returns true
        assertTrue(expiryDate.equals(new OrganExpiryDate("23-Jan-2020")));
        assertTrue(expiryDate.equals(new OrganExpiryDate("23-JAN-2020")));
        assertTrue(expiryDate.equals(new OrganExpiryDate("23-JANuary-2020")));
        assertTrue(expiryDate.equals(new OrganExpiryDate("23-january-2020")));
    }

    @Test
    public void hashCodeTest() {
        OrganExpiryDate expiryDate = new OrganExpiryDate("23-Jan-2020");

        assertEquals(expiryDate.hashCode(), new OrganExpiryDate("23-Jan-2020").hashCode());
        assertEquals(expiryDate.hashCode(), new OrganExpiryDate("23-JAN-2020").hashCode());
        assertEquals(expiryDate.hashCode(), new OrganExpiryDate("23-JANuaRy-2020").hashCode());
        assertNotEquals(expiryDate.hashCode(), new OrganExpiryDate("23-May-2020").hashCode());
        assertNotEquals(expiryDate.hashCode(), new OrganExpiryDate("24-Jan-2022").hashCode());
    }
}
