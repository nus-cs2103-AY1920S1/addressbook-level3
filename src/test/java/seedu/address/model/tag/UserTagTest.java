package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.model.tag.exceptions.InvalidTagNameException;
import seedu.address.testutil.TagBuilder;

public class UserTagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UserTag(null));
    }

    @Test
    public void constructor_invalidTagNameFormat_throwsInvalidTagNameException() {
        String invalidTagName = "";
        assertThrows(InvalidTagNameException.class, () -> new UserTag(invalidTagName));
    }

    @Test
    public void constructor_invalidDefaultTagName_throwsInvalidTagNameException() {
        String invalidTagNameOne = "Completed";
        assertThrows(InvalidTagNameException.class, () -> new UserTag(invalidTagNameOne));
        String invalidTagNameTwo = "Core";
        assertThrows(InvalidTagNameException.class, () -> new UserTag(invalidTagNameTwo));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> UserTag.isValidTagName(null));
        // valid tag name
        String validTagName = "Exchange";
        assertTrue(UserTag.isValidTagName(validTagName));
    }

    @Test
    public void isDefault() {
        assertFalse(TagBuilder.buildUserTag().isDefault());
    }

    @Test
    public void getTagName() {
        assertEquals("testTag", TagBuilder.buildUserTag().getTagName());
    }

    @Test
    public void rename() {
        UserTag tag = TagBuilder.buildUserTag();
        String newName = "newName";
        tag.rename(newName);
        assertEquals("newName", tag.getTagName());
    }

    @Test
    public void isSameTag_returnsTrue() {
        assertTrue(TagBuilder.buildUserTag().isSameTag(TagBuilder.buildUserTag()));
        assertTrue(TagBuilder.buildUserTag("userTag").isSameTag(TagBuilder.buildUserTag("userTag")));
    }

    @Test
    public void isSameTag_returnsFalse() {
        assertFalse(TagBuilder.buildUserTag().isSameTag(TagBuilder.buildUserTag("differentName")));
    }

    @Test
    public void equals() {
        UserTag tag = TagBuilder.buildUserTag();

        // same object
        assertTrue(tag.equals(tag));

        // same name
        assertTrue(tag.equals(TagBuilder.buildUserTag()));

        // different name
        assertFalse(tag.equals(TagBuilder.buildUserTag("differentName")));
    }

    @Test
    public void toString_stringReturned() {
        assertEquals("[testTag]", TagBuilder.buildUserTag().toString());
    }

}
