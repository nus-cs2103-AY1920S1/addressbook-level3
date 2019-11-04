package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Tag is compulsory for all events
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    void isValidDate() {
        // null tag
        assertThrows(NullPointerException.class, () -> Tag.isValidTag(null));

        // valid tag
        assertTrue(Tag.isValidTag("Priority: Critical"));

        assertTrue(Tag.isValidTag("Priority: High"));

        assertTrue(Tag.isValidTag("Priority: Medium"));

        assertTrue(Tag.isValidTag("Priority: Low"));

        assertTrue(Tag.isValidTag("Priority: None"));
    }

    @Test
    void testToString() {
        Tag tag = new Tag("Priority: Critical");
        Tag tag1 = new Tag("Priority: High");
        Tag tag2 = new Tag("Priority: Medium");
        Tag tag3 = new Tag("Priority: Low");
        Tag tag4 = new Tag("Priority: None");

        // Testing different expected values
        assertNotEquals("Priority: Immediate", tag.toString());

        // Since tag is determined by the dropdown combo box, all the values are fixed.
        assertEquals("Priority: Critical", tag.toString());

        assertEquals("Priority: High", tag1.toString());

        assertEquals("Priority: Medium", tag2.toString());

        assertEquals("Priority: Low", tag3.toString());

        assertEquals("Priority: None", tag4.toString());
    }
}
