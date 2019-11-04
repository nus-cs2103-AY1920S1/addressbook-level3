package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    void testEquals() {
        Description description = new Description("This module is awesome.");

        // same values -> return true
        assertTrue(description.equals(new Description("This module is awesome.")));

        // same object -> return true
        assertTrue(description.equals(description));

        // null -> returns false
        assertFalse(description.equals(null));

        // different description -> returns false
        assertFalse(description.equals(new Description("This module is bad.")));
    }

    @Test
    void testHashCode() {
        Description m1 = new Description("This module is awesome.");
        Description m2 = new Description("This module is awesome.");
        Description m3 = new Description("This module is bad.");
        assertEquals(m1.hashCode(), m1.hashCode());
        assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1.hashCode(), m3.hashCode());
    }
}
