package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateOfBirthTest {

    @Test
    public void isValidDateOfBirth_emptyString() {
        assertTrue(DateOfBirth.isValidDateOfBirth(""));
    }

    @Test
    public void isValidDateOfBirth_firstExampleDateOfBirth() {
        assertTrue(DateOfBirth.isValidDateOfBirth("1920-11-20"));
    }

    @Test
    public void isValidDateOfBirth_secondExampleDateOfBirth() {
        assertTrue(DateOfBirth.isValidDateOfBirth("1900-02-28"));
    }

    @Test
    public void isValidDateOfBirth_firstInvalidDateOfBirth() {
        assertFalse(DateOfBirth.isValidDateOfBirth("2019-02-29"));
    }

    @Test
    public void isValidDateOfBirth_secondInvalidDateOfBirth() {
        assertFalse(DateOfBirth.isValidDateOfBirth("2020/12/20"));
    }

    @Test
    public void isValidDateOfBirth_thirdInvalidDateOfBirth() {
        assertFalse(DateOfBirth.isValidDateOfBirth("2020-13-20"));
    }


    @Test
    public void testToString() {
        assertEquals("1900-02-28", (new DateOfBirth("1900-02-28")).toString());
    }

    @Test
    public void testEquals_sameDateOfBirth() {
        assertEquals(new DateOfBirth("1900-02-28"),
                new DateOfBirth("1900-02-28"));
    }

    @Test
    public void testEquals_differentDateOfBirth() {
        assertNotEquals(new DateOfBirth("1900-02-28"),
                new DateOfBirth("1901-02-28"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new DateOfBirth("1901-02-28"));
    }

    @Test
    public void testHashCode_sameDateOfBirth() {
        assertEquals(new DateOfBirth("1900-02-28").hashCode(),
                new DateOfBirth("1900-02-28").hashCode());
    }

    @Test
    public void testHashCode_differentDateOfBirth() {
        assertNotEquals(new DateOfBirth("1900-02-28").hashCode(),
                new DateOfBirth("1901-02-28").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new DateOfBirth("1901-02-28").hashCode());
    }
}
