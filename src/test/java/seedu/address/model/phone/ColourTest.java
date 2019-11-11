package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ColourTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Colour(null));
    }

    @Test
    public void constructor_invalidColour_throwsIllegalArgumentException() {
        String invalidColour = "";
        assertThrows(IllegalArgumentException.class, () -> new Colour(invalidColour));
    }

    @Test
    void isValidColour() {
        // null colour
        assertThrows(NullPointerException.class, () -> Colour.isValidColour(null));

        // invalid colour
        assertFalse(Colour.isValidColour("")); // empty string
        assertFalse(Colour.isValidColour(" ")); // spaces only

        // valid colour
        assertTrue(Colour.isValidColour("hot pink")); // alphabets only
        assertTrue(Colour.isValidColour("1337")); // numbers only
        assertTrue(Colour.isValidColour("rainbow 7")); // alphanumeric characters
        assertTrue(Colour.isValidColour("Phthalo Blue")); // with capital letters
        assertTrue(Colour.isValidColour("Antique white with a tinge of cyan and a smudge of green")); // long names
        assertTrue(Colour.isValidColour("Blue + Green")); // with symbols
    }
}
