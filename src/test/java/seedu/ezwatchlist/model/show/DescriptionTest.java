package seedu.ezwatchlist.model.show;

import org.junit.jupiter.api.Test;
import seedu.ezwatchlist.model.show.Description;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

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

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid description
        assertTrue(Description.isValidDescription("A magical Yeti must return to his family."));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("In Gotham City, mentally-troubled comedian Arthur Fleck is "
                + "disregarded and mistreated by society. He then embarks on a downward spiral of revolution"
                + " and bloody crime. This path brings him face-to-face with "
                + "his alter-ego: The Joker.")); // long description
    }
}
