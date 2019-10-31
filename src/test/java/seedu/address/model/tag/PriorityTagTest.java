package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TagBuilder;

public class PriorityTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PriorityTag(null));
    }

    @Test
    public void isValidTagName_returnsTrue() {
        assertTrue(PriorityTag.isValidTagName("HIGH"));
        assertTrue(PriorityTag.isValidTagName("high"));
        assertTrue(PriorityTag.isValidTagName("MEDIUM"));
        assertTrue(PriorityTag.isValidTagName("LOW"));
    }

    @Test
    public void isValidTagName_returnsFalse() {
        assertFalse(PriorityTag.isValidTagName("IMPORTANT"));
        assertFalse(PriorityTag.isValidTagName("PRIORITY.HIGH"));
        assertFalse(PriorityTag.isValidTagName(""));
    }

    @Test
    public void isDefault() {
        assertFalse(new TagBuilder().buildPriorityHighTag().isDefault());
    }

    @Test
    public void isPriority() {
        assertTrue(new TagBuilder().buildPriorityHighTag().isPriority());
    }

    @Test
    public void getTagName() {
        assertEquals("PRIORITY.HIGH", new TagBuilder().buildPriorityHighTag().getTagName());
    }

    @Test
    public void getPriorityTagType() {
        PriorityTag priorityTag = new TagBuilder().buildPriorityHighTag();
        assertEquals(PriorityTagType.HIGH, priorityTag.getPriorityTagType());
    }

    @Test
    public void isSameTag_returnsTrue() {
        assertTrue(new TagBuilder().buildPriorityHighTag()
                .isSameTag(new TagBuilder().buildPriorityHighTag()));
        assertTrue(new TagBuilder().buildPriorityTag(PriorityTagType.MEDIUM)
                .isSameTag(new TagBuilder().buildPriorityTag(PriorityTagType.MEDIUM)));
    }

    @Test
    public void isSameTag_returnsFalse() {
        assertFalse(new TagBuilder().buildPriorityHighTag()
                .isSameTag(new TagBuilder().buildPriorityTag(PriorityTagType.LOW)));
        assertFalse(new TagBuilder().buildPriorityHighTag()
                .isSameTag(new TagBuilder().buildDefaultCoreTag()));
    }

    @Test
    public void equals() {
        PriorityTag tag = new TagBuilder().buildPriorityHighTag();

        // same object
        assertTrue(tag.equals(tag));

        // same default tag type
        assertTrue(tag.equals(new TagBuilder().buildPriorityHighTag()));

        // different default tag type
        assertFalse(tag.equals(new TagBuilder().buildPriorityTag(PriorityTagType.MEDIUM)));

        // different type of tag
        assertFalse(tag.equals(new TagBuilder().buildTestUserTag()));

        // hashcode
        // same tag
        assertEquals(tag.hashCode(), tag.hashCode());

        // same default tag type
        assertEquals(tag.hashCode(), (new TagBuilder().buildPriorityHighTag()).hashCode());

        // different default tag type
        assertNotEquals(tag.hashCode(), (new TagBuilder().buildPriorityTag(PriorityTagType.MEDIUM)).hashCode());

        // different tag type
        assertNotEquals(tag.hashCode(), (new TagBuilder().buildTestUserTag().hashCode()));

    }

    @Test
    public void toString_stringReturned() {
        assertEquals("[PRIORITY.HIGH]", new TagBuilder().buildPriorityHighTag().toString());
    }

}
