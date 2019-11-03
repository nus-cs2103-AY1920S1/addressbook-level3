package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
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
        assertEquals(tag.toString(), "Priority: Critical");
    }
}
