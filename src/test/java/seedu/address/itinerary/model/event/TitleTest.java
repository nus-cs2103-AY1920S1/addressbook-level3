package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
                + "0000000000000000000000000000000000000")); // exceed 70 character limit

        // valid Title
        assertTrue(Title.isValidTitle(" ")); // spaces only
        assertTrue(Title.isValidTitle(
                "0000000000000000000000000000000000"
                        + "0000000000000000000000000000000000")); // Just nice 70 character limit
        assertTrue(Title.isValidTitle("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π"));
    }

    @Test
    void testToString() {
        Title title = new Title("Awesome Title");
        assertEquals(title.toString(), "Awesome Title");
    }
}
