package seedu.address.model.card;

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
        String emptyDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(emptyDescription));
        StringBuilder tooLongDescription = new StringBuilder();
        for (int i = 0; i < Description.MAX_LEN + 1; ++i) {
            tooLongDescription.append("a");
        }
        assertThrows(IllegalArgumentException.class, () -> new Description(tooLongDescription.toString()));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("I need help."));
        assertTrue(Description.isValidDescription("Ineedhelp."));
        assertTrue(Description.isValidDescription("-")); // one character
        StringBuilder maxLenDescription = new StringBuilder();
        for (int i = 0; i < Description.MAX_LEN; ++i) {
            maxLenDescription.append("a");
        }
        assertTrue(Description.isValidDescription(maxLenDescription.toString())); // maximum length
    }
}
