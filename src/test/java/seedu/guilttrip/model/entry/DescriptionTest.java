package seedu.guilttrip.model.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidEntryDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidEntryDescription));
    }

    @Test
    public void isValidDescription() {
        // null name
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid name
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid name
        assertTrue(Description.isValidDescription("mala at deck")); // alphabets only
        assertTrue(Description.isValidDescription("6667")); // numbers only
        assertTrue(Description.isValidDescription("mala the 4th")); // alphanumeric characters
        assertTrue(Description.isValidDescription("MALAAAAAAA omg")); // with capital letters
        assertTrue(Description.isValidDescription("Deck mala Pines mala Home Mala Utown Mala 4")); // long names
    }
}
