package seedu.address.model.itinerary;

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
        String invalidDescription = "\n";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValid() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("My Description"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Lorem Ipsum is simply dummy text of the printing "
                + "and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever "
                + "since the 1500s, when an unknown printer took a galley of type and scrambled it to make "
                + "a type specimen book. It has survived not only five centuries, but also the leap into "
                + "electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s "
                + "with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with "
                + "desktop publishing software like Aldus PageMaker "
                + "including versions of Lorem Ipsum.")); // long description

    }
}
