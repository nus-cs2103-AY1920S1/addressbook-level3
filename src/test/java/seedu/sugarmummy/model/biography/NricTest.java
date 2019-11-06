package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NricTest {

    @Test
    public void isValidNric_emptyString() {
        assertTrue(Nric.isValidNric(""));
    }

    @Test
    public void isValidNric_firstExampleNric() {
        assertTrue(Nric.isValidNric("S1234567A"));
    }

    @Test
    public void isValidNric_secondExampleNric() {
        assertTrue(Nric.isValidNric("Z9876543C"));
    }

    @Test
    public void isValidNric_firstInvalidNric() {
        assertFalse(Nric.isValidNric("S-78921@X"));
    }

    @Test
    public void isValidNric_secondInvalidNric() {
        assertFalse(Nric.isValidNric("S\'8342`Z"));
    }

    @Test
    public void testToString() {
        assertEquals("S1234567A", (new Nric("S1234567A")).toString());
    }

    @Test
    public void testEquals_sameNric() {
        assertEquals(new Nric("S1234567A"),
                new Nric("S1234567A"));
    }

    @Test
    public void testEquals_differentNric() {
        assertNotEquals(new Nric("S1234567A"),
                new Nric("S1234567B"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new Nric("S1234567A"));
    }

    @Test
    public void testHashCode_sameNric() {
        assertEquals(new Nric("S1234567A").hashCode(),
                new Nric("S1234567A").hashCode());
    }

    @Test
    public void testHashCode_differentNric() {
        assertNotEquals(new Nric("S1234567A").hashCode(),
                new Nric("S1234567B").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new Nric("S1234567A").hashCode());
    }
}
