package seedu.algobase.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    public void testGetName() {
        Tag tag = new Tag("Difficult");
        assertEquals("Difficult", tag.getName());
    }

    @Test
    public void testEquals() {
        Tag tag = new Tag("Easy");
        assertFalse(tag.equals(new Tag("easy")));
        assertTrue(tag.equals(new Tag("Easy")));
    }

    @Test
    public void testIsValidTagName() {
        assertFalse(Tag.isValidTagName("very difficult"));
        assertTrue(Tag.isValidTagName("Sort"));
    }
}
