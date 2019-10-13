package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {
    private static final Priority PRIORITY_HIGH = new Priority(Priority.PRIORITY_HIGH);
    private static final Priority PRIORITY_MEDIUM = new Priority(Priority.PRIORITY_MEDIUM);
    private static final Priority PRIORITY_LOW = new Priority(Priority.PRIORITY_LOW);

    private static final Priority PRIORITY_HIGH_CAPS = new Priority(Priority.PRIORITY_HIGH.toUpperCase());
    private static final Priority PRIORITY_MEDIUM_CAPS = new Priority(Priority.PRIORITY_MEDIUM.toUpperCase());
    private static final Priority PRIORITY_LOW_CAPS = new Priority(Priority.PRIORITY_LOW.toUpperCase());

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidPriority = "";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null type
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid type
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("^")); // only non-alphanumeric characters
        assertFalse(Priority.isValidPriority("peter*")); // contains non-alphanumeric characters

        assertFalse(Priority.isValidPriority("12345")); // numbers only
        assertFalse(Priority.isValidPriority("peter the 2nd")); // alphanumeric characters
        assertFalse(Priority.isValidPriority("Capital Tan")); // with capital letters

        assertFalse(Priority.isValidPriority("medium high")); //permutations of high, medium and low
        assertFalse(Priority.isValidPriority("highlow"));

        assertTrue(Priority.isValidPriority("high")); //high is valid
        assertTrue(Priority.isValidPriority("medium")); //medium is valid
        assertTrue(Priority.isValidPriority("low")); //low is valid
        assertTrue(Priority.isValidPriority("HIGH")); //case insensitive
        assertTrue(Priority.isValidPriority("MEDIUM")); //case insensitive
        assertTrue(Priority.isValidPriority("LOW")); //case insensitive
    }

    @Test
    public void isHighPriority() {
        assertTrue(PRIORITY_HIGH.isHighPriority()); //valid case
        assertTrue(PRIORITY_HIGH_CAPS.isHighPriority()); //case insensitive

        assertFalse(PRIORITY_LOW.isHighPriority()); //not valid, is low
        assertFalse(PRIORITY_LOW_CAPS.isHighPriority()); //not valid, is low (caps)

        assertFalse(PRIORITY_MEDIUM.isHighPriority()); //not valid, is medium
        assertFalse(PRIORITY_MEDIUM_CAPS.isHighPriority()); //not valid, is medium (caps)
    }

    @Test
    public void isMediumPriority() {
        assertTrue(PRIORITY_MEDIUM.isMediumPriority()); //valid case
        assertTrue(PRIORITY_MEDIUM_CAPS.isMediumPriority()); //case insensitive

        assertFalse(PRIORITY_LOW.isMediumPriority()); //not valid, is low
        assertFalse(PRIORITY_LOW_CAPS.isMediumPriority()); //not valid, is low (caps)

        assertFalse(PRIORITY_HIGH.isMediumPriority()); //not valid, is high
        assertFalse(PRIORITY_HIGH_CAPS.isMediumPriority()); //not valid, is high (caps)
    }

    @Test
    public void isLowPriority() {
        assertTrue(PRIORITY_LOW.isLowPriority()); //valid case
        assertTrue(PRIORITY_LOW_CAPS.isLowPriority()); //case insensitive

        assertFalse(PRIORITY_HIGH.isLowPriority()); //not valid, is high
        assertFalse(PRIORITY_HIGH_CAPS.isLowPriority()); //not valid, is high (caps)

        assertFalse(PRIORITY_MEDIUM.isLowPriority()); //not valid, is medium
        assertFalse(PRIORITY_MEDIUM_CAPS.isLowPriority()); //not valid, is medium (caps)

    }

    @Test
    public void toString_allValidInputs() {
        assertEquals(PRIORITY_HIGH.toString(), "high");
        assertEquals(PRIORITY_HIGH_CAPS.toString(), "high"); //converted to lowercase

        assertEquals(PRIORITY_LOW.toString(), "low");
        assertEquals(PRIORITY_LOW_CAPS.toString(), "low");

        assertEquals(PRIORITY_MEDIUM.toString(), "medium");
        assertEquals(PRIORITY_MEDIUM_CAPS.toString(), "medium");
    }

    @Test
    public void equals_validEquals() {
        assertTrue(PRIORITY_HIGH.equals(PRIORITY_HIGH)); //same object
        assertTrue(PRIORITY_MEDIUM.equals(PRIORITY_MEDIUM)); //same object
        assertTrue(PRIORITY_HIGH.equals(PRIORITY_HIGH_CAPS)); //case insensitive


        assertFalse(PRIORITY_HIGH.equals(null)); //null object
        assertFalse(PRIORITY_LOW.equals("low")); //subject object
    }

    @Test
    public void hashCodeTest() {
        assertEquals(PRIORITY_HIGH.hashCode(), PRIORITY_HIGH.hashCode());
        assertEquals(PRIORITY_HIGH.hashCode(), PRIORITY_HIGH_CAPS.hashCode());
        assertNotEquals(PRIORITY_HIGH.hashCode(), PRIORITY_LOW.hashCode());
        assertNotEquals(PRIORITY_HIGH.hashCode(), PRIORITY_MEDIUM.hashCode());

    }
}
