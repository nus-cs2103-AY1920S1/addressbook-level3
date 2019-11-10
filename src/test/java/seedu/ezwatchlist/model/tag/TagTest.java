package seedu.ezwatchlist.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void tagConstructor() {
        assertEquals(new Tag("test").tagName, "test");

    }
    @Test
    void isValidTagName() {
        assertTrue(Tag.isValidTagName("test"));
    }

    @Test
    void testEquals() {
        Tag tag = new Tag("test");
        Tag tag1 = new Tag("test1");
        assertTrue(tag.equals(tag));
        assertFalse(tag1.equals(tag));
    }

    @Test
    void testHashCode() {
        assertEquals(new Tag("test").hashCode(), "test".hashCode());

    }

    @Test
    void testToString() {
        assertEquals(new Tag("test").toString(), '[' + "test" + ']');
    }
}
