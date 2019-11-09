package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.scheduler.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.model.tag.Tag;

class JsonAdaptedTagTest {

    @Test
    public void getTagName_validTagString_success() {
        JsonAdaptedTag tagFriend = new JsonAdaptedTag(VALID_TAG_FRIEND);
        JsonAdaptedTag tagHusband = new JsonAdaptedTag(VALID_TAG_HUSBAND);
        assertEquals(VALID_TAG_FRIEND, tagFriend.getTagName());
        assertEquals(VALID_TAG_HUSBAND, tagHusband.getTagName());
    }

    @Test
    public void getTagName_validTagObject_success() {
        Tag tagFriend = new Tag(VALID_TAG_FRIEND);
        Tag tagHusband = new Tag(VALID_TAG_HUSBAND);
        JsonAdaptedTag jsonTagFriend = new JsonAdaptedTag(tagFriend);
        JsonAdaptedTag jsonTagHusband = new JsonAdaptedTag(tagHusband);
        assertEquals(VALID_TAG_FRIEND, jsonTagFriend.getTagName());
        assertEquals(VALID_TAG_HUSBAND, jsonTagHusband.getTagName());
    }

    @Test
    public void toModelType_validTag_success() throws Exception {
        JsonAdaptedTag tagFriend = new JsonAdaptedTag(VALID_TAG_FRIEND);
        JsonAdaptedTag tagHusband = new JsonAdaptedTag(VALID_TAG_HUSBAND);
        assertEquals(new Tag(VALID_TAG_FRIEND), tagFriend.toModelType());
        assertEquals(new Tag(VALID_TAG_HUSBAND), tagHusband.toModelType());
    }

    @Test
    public void toModelType_invalidDepartment_throwsIllegalValueException() {
        JsonAdaptedTag tag = new JsonAdaptedTag("    ");
        assertThrows(IllegalValueException.class, Tag.MESSAGE_CONSTRAINTS, tag::toModelType);
    }
}
