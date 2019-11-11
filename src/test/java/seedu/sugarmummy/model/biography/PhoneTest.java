package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    public void isValidPhone_emptyString() {
        assertFalse(Phone.isValidPhone(""));
    }

    @Test
    public void isValidPhone_firstExamplePhone() {
        assertTrue(Phone.isValidPhone("91234567"));
    }

    @Test
    public void isValidPhone_secondExamplePhone() {
        assertTrue(Phone.isValidPhone("6581234567"));
    }

    @Test
    public void isValidPhone_firstInvalidPhone() {
        assertFalse(Phone.isValidPhone("2345@835"));
    }

    @Test
    public void isValidPhone_secondInvalidPhone() {
        assertFalse(Phone.isValidPhone("65-87654321"));
    }

    @Test
    public void testToString() {
        assertEquals("91234567", (new Phone("91234567")).toString());
    }

    @Test
    public void testEquals_samePhone() {
        assertEquals(new Phone("91234567"),
                new Phone("91234567"));
    }

    @Test
    public void testEquals_differentPhone() {
        assertNotEquals(new Phone("91234567"),
                new Phone("81234567"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new Phone("91234567"));
    }

    @Test
    public void testHashCode_samePhone() {
        assertEquals(new Phone("91234567").hashCode(),
                new Phone("91234567").hashCode());
    }

    @Test
    public void testHashCode_differentPhone() {
        assertNotEquals(new Phone("91234567").hashCode(),
                new Phone("81234567").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new Phone("91234567").hashCode());
    }
}
