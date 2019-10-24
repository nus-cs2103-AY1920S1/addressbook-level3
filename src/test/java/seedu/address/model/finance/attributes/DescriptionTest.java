package seedu.address.model.finance.attributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("Yong Tau Foo"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("烧鸡饭加菜和饭")); // chinese characters
        assertTrue(Description.isValidDescription("2 pieces of KFC tenders")); // alpnumeric characters
        assertTrue(Description.isValidDescription("Maccas Breakfast Deluxe: Kiwi burger, hashbrowns,"
                + "pancakes, scrambled eggs, butter")); // long description

    }
}
