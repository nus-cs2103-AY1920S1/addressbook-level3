package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    void isValidTitle() {
        // null Title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid Title
        assertFalse(Title.isValidTitle("")); // empty string

        assertFalse(Title.isValidTitle(
                "0000000000000000000000000000000000"
                + "0000000000000000000000000000000000000")); // exceed 70 character limit, 71 characters

        // valid Title
        assertTrue(Title.isValidTitle(" ")); // spaces only

        assertTrue(Title.isValidTitle("This is a valid title")); // A valid title

        assertTrue(Title.isValidTitle(
                "0000000000000000000000000000000000"
                        + "0000000000000000000000000000000000")); // Just nice 70 character limit

        assertTrue(Title.isValidTitle("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π")); // Special characters
    }

    @Test
    void testToString() {
        Title title = new Title("Awesome Title");
        Title title2 = new Title(" ");
        Title title3 = new Title("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π");
        Title title4 = new Title("0000000000000000000000000000000000"
                + "0000000000000000000000000000000000");

        // Testing different expected values
        // Empty title field
        assertNotEquals("", title);

        // Only valid title which pass the validation test will be created
        assertEquals("Awesome Title", title.toString());

        assertEquals(" ", title2.toString());

        assertEquals("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π", title3.toString());

        assertEquals("0000000000000000000000000000000000"
                + "0000000000000000000000000000000000", title4.toString());
    }
}
