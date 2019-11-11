package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    public void isValidName_emptyString() {
        assertFalse(Name.isValidName(""));
    }

    @Test
    public void isValidName_firstExampleName() {
        assertTrue(Name.isValidName("John Doe"));
    }

    @Test
    public void isValidName_secondExampleName() {
        assertTrue(Name.isValidName("Amy"));
    }

    @Test
    public void isValidName_firstInvalidName() {
        assertFalse(Name.isValidName("Amy1920"));
    }

    @Test
    public void isValidName_secondInvalidName() {
        assertFalse(Name.isValidName("John-Doe-is-awesome"));
    }

    @Test
    public void testToString() {
        assertEquals("John Doe", (new Name("John Doe")).toString());
    }

    @Test
    public void testEquals_sameName() {
        assertEquals(new Name("John Doe"),
                new Name("John Doe"));
    }

    @Test
    public void testEquals_differentName() {
        assertNotEquals(new Name("John Doe"),
                new Name("john doe"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new Name("John Doe"));
    }

    @Test
    public void testHashCode_sameName() {
        assertEquals(new Name("John Doe").hashCode(),
                new Name("John Doe").hashCode());
    }

    @Test
    public void testHashCode_differentName() {
        assertNotEquals(new Name("John Doe").hashCode(),
                new Name("john doe").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new Name("John Doe").hashCode());
    }
}
