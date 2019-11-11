package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GenderTest {

    @Test
    public void isValidGender_emptyString() {
        assertTrue(Gender.isValidGender(""));
    }

    @Test
    public void isValidGender_firstExampleGender() {
        assertTrue(Gender.isValidGender("Female"));
    }

    @Test
    public void isValidGender_secondExampleGender() {
        assertTrue(Gender.isValidGender("Male"));
    }

    @Test
    public void isValidGender_firstInvalidGender() {
        assertFalse(Gender.isValidGender("Male-boi"));
    }

    @Test
    public void isValidGender_secondInvalidGender() {
        assertFalse(Gender.isValidGender("Male@97"));
    }

    @Test
    public void testToString() {
        assertEquals("Female", (new Gender("Female")).toString());
    }

    @Test
    public void testEquals_sameGender() {
        assertEquals(new Gender("Female"),
                new Gender("Female"));
    }

    @Test
    public void testEquals_differentGender() {
        assertNotEquals(new Gender("Female"),
                new Gender("Male"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new Gender("Female"));
    }

    @Test
    public void testHashCode_sameGender() {
        assertEquals(new Gender("Female").hashCode(),
                new Gender("Female").hashCode());
    }

    @Test
    public void testHashCode_differentGender() {
        assertNotEquals(new Gender("Female").hashCode(),
                new Gender("Male").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new Gender("Female").hashCode());
    }
}
