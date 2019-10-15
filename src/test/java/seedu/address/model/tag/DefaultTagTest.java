package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertTrue(TagBuilder.buildDefaultCoreTag().isDefault());
    }

    @Test
    public void getTagName() {
        assertEquals("Core", TagBuilder.buildDefaultCoreTag().getTagName());
    }

    @Test
    public void getDefaultTagType() {
        DefaultTag defaultTag = TagBuilder.buildDefaultCoreTag();
        assertEquals(DefaultTagType.CORE, defaultTag.getDefaultTagType());
    }

    @Test
    public void isSameTag_returnsTrue() {
        assertTrue(TagBuilder.buildDefaultCoreTag().isSameTag(TagBuilder.buildDefaultCoreTag()));
        assertTrue(TagBuilder.buildDefaultTag(DefaultTagType.CORE).
                isSameTag(TagBuilder.buildDefaultTag(DefaultTagType.CORE)));
    }

    @Test
    public void isSameTag_returnsFalse() {
        assertFalse(TagBuilder.buildDefaultCoreTag().
                isSameTag(TagBuilder.buildDefaultTag(DefaultTagType.COMPLETED)));
    }

    @Test
    public void equals() {
        DefaultTag tag = TagBuilder.buildDefaultCoreTag();

        // same object
        assertTrue(tag.equals(tag));

        // same default tag type
        assertTrue(tag.equals(TagBuilder.buildDefaultCoreTag()));

        // different default tag type
        assertFalse(tag.equals(TagBuilder.buildDefaultTag(DefaultTagType.COMPLETED)));

        // different type of tag
        assertFalse(tag.equals(TagBuilder.buildTestUserTag()));
    }

    @Test
    public void toString_stringReturned() {
        assertEquals("[Core]", TagBuilder.buildDefaultCoreTag().toString());
    }

}
