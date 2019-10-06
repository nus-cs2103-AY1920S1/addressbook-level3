package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void isValidDescription() {
        // null weme
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // valid description
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("Surprised Pikachu Meme"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription(
                "Roses are red Violets are blue Every man is cool Until the Cockroach flew")); // long weme
    }
}
