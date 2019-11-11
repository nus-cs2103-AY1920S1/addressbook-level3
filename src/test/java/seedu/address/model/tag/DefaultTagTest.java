package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TagBuilder;

public class DefaultTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DefaultTag(null));
    }

    @Test
    public void isDefault() {
        assertTrue(new TagBuilder().buildDefaultCoreTag().isDefault());
    }

    @Test
    public void isPriority() {
        assertFalse(new TagBuilder().buildDefaultCoreTag().isPriority());
    }

    @Test
    public void getTagName() {
        assertEquals("Core", new TagBuilder().buildDefaultCoreTag().getTagName());
    }

    @Test
    public void getDefaultTagType() {
        DefaultTag defaultTag = new TagBuilder().buildDefaultCoreTag();
        assertEquals(DefaultTagType.CORE, defaultTag.getDefaultTagType());
    }

    @Test
    public void getDescription() {
        DefaultTag defaultTag = new TagBuilder().buildDefaultCoreTag();
        assertEquals(DefaultTagType.CORE.getDescription(), defaultTag.getDescription());
    }

    @Test
    public void isSameTag_returnsTrue() {
        assertTrue(new TagBuilder().buildDefaultCoreTag()
                .isSameTag(new TagBuilder().buildDefaultCoreTag()));
        assertTrue(new TagBuilder().buildDefaultTag(DefaultTagType.CORE)
                .isSameTag(new TagBuilder().buildDefaultTag(DefaultTagType.CORE)));
    }

    @Test
    public void isSameTag_returnsFalse() {
        assertFalse(new TagBuilder().buildDefaultCoreTag()
                .isSameTag(new TagBuilder().buildDefaultTag(DefaultTagType.COMPLETED)));
    }

    @Test
    public void equals() {
        DefaultTag tag = new TagBuilder().buildDefaultCoreTag();

        // same object
        assertTrue(tag.equals(tag));

        // same default tag type
        assertTrue(tag.equals(new TagBuilder().buildDefaultCoreTag()));

        // different default tag type
        assertFalse(tag.equals(new TagBuilder().buildDefaultTag(DefaultTagType.COMPLETED)));

        // different type of tag
        assertFalse(tag.equals(new TagBuilder().buildTestUserTag()));

        // hashcode
        // same tag
        assertEquals(tag.hashCode(), tag.hashCode());

        // same default tag type
        assertEquals(tag.hashCode(), (new TagBuilder().buildDefaultCoreTag()).hashCode());

        // different default tag type
        assertNotEquals(tag.hashCode(), (new TagBuilder().buildDefaultTag(DefaultTagType.AI_E)).hashCode());

        // different tag type
        assertNotEquals(tag.hashCode(), (new TagBuilder().buildTestUserTag().hashCode()));
    }

    @Test
    public void toString_stringReturned() {
        assertEquals("[Core]", new TagBuilder().buildDefaultCoreTag().toString());
    }

}
