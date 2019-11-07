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
        assertFalse(new TagBuilder().buildTestUserTag().isDefault());
    }

    @Test
    public void isPriority() {
        assertFalse(new TagBuilder().buildDefaultCoreTag().isPriority());
    }

    @Test
    public void getTagName() {
        assertEquals("testUserTag", new TagBuilder().buildTestUserTag().getTagName());
    }

    @Test
    public void rename() {
        UserTag tag = new TagBuilder().buildTestUserTag();
        String newName = "newName";
        tag.rename(newName);
        assertEquals("newName", tag.getTagName());
    }

    @Test
    public void isSameTag_returnsTrue() {
        assertTrue(new TagBuilder().buildTestUserTag().isSameTag(new TagBuilder().buildTestUserTag()));
        assertTrue(new TagBuilder().buildUserTag("userTag")
                .isSameTag(new TagBuilder().buildUserTag("userTag")));
        assertTrue(new TagBuilder().buildUserTag("UserTag")
                .isSameTag(new TagBuilder().buildUserTag("userTag")));
    }

    @Test
    public void isSameTag_returnsFalse() {
        assertFalse(new TagBuilder().buildTestUserTag().isSameTag(new TagBuilder().buildUserTag("differentName")));
    }

    @Test
    public void equals() {
        UserTag tag = new TagBuilder().buildTestUserTag();

        // same object
        assertTrue(tag.equals(tag));

        // same name
        assertTrue(tag.equals(new TagBuilder().buildTestUserTag()));

        // same name, different case
        assertTrue(tag.equals(new TagBuilder().buildUserTag("testusertag")));

        // different name
        assertFalse(tag.equals(new TagBuilder().buildUserTag("differentName")));

        // different type of tag
        assertFalse(tag.equals(new TagBuilder().buildDefaultCoreTag()));
    }

    @Test
    public void toString_stringReturned() {
        assertEquals("[testUserTag]", new TagBuilder().buildTestUserTag().toString());
    }

}
