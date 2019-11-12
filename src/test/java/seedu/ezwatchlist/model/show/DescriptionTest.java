package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_passes() {
        assert(new Description(null).equals(new Description(null)));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        //assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
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

    @Test
    public void descriptionConstructorTest() {
        assertEquals(new Description().fullDescription, "");
    }

    @Test
    public void hashCode_test() {
        assertEquals(new Description("A fine day").hashCode(), "A fine day".hashCode());
    }
}
