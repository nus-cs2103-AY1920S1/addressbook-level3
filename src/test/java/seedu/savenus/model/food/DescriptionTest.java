package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isBlankDescription() {
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("           ")); // tons of spaces
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // valid description
        assertTrue(Description.isValidDescription("prawn noodles"));
        assertTrue(Description.isValidDescription("good")); // minimal
        assertTrue(Description.isValidDescription("chicken with plenty rice")); // alphabets only
    }

    @Test
    public void get_field_test() {
        String sampleString = "Chinese";
        assertEquals(new Description(sampleString).getField(), sampleString);
        assertNotEquals(new Description(sampleString).getField(), "");
    }

    @Test
    public void compareTests() {
        Description normalDescription = new Description("Malay");
        assertEquals(normalDescription.compareTo(null), 1);
        assertEquals(normalDescription.compareTo(normalDescription), 0);
    }
}
