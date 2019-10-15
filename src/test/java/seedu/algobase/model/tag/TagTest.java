package seedu.algobase.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void testGetName() {
        Tag tag = new Tag("Difficult");
        assertEquals("Difficult", tag.getName());
    }

    @Test
    public void testEquals() {
        Tag tag = new Tag("Easy");
        assertEquals(false, tag.equals(new Tag("easy")));
        assertEquals(true, tag.equals(new Tag("Easy")));
    }

    @Test
    public void testIsValidTagName() {
        assertEquals(false, Tag.isValidTagName("very difficult"));
        assertEquals(true, Tag.isValidTagName("Sort"));
    }
}
