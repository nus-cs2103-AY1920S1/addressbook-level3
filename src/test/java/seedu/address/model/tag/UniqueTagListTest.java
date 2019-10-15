package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.InvalidTagModificationException;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.TagBuilder;

public class UniqueTagListTest {

    private final UniqueTagList uniqueTagList = new UniqueTagList();

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.contains(null));
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.contains(TagBuilder.buildDefaultCoreTag()));
        assertFalse(uniqueTagList.contains(TagBuilder.buildTestUserTag()));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        DefaultTag defaultTag = TagBuilder.buildDefaultCoreTag();
        uniqueTagList.addTag(defaultTag);
        assertTrue(uniqueTagList.contains(defaultTag));

        UserTag userTag = TagBuilder.buildTestUserTag();
        uniqueTagList.addTag(userTag);
        assertTrue(uniqueTagList.contains(userTag));
    }

    @Test
    public void contains_tagWithSameNameInList_returnsTrue() {
        uniqueTagList.addTag(TagBuilder.buildUserTag("exchange"));
        assertTrue(uniqueTagList.contains(TagBuilder.buildUserTag("exchange")));

        uniqueTagList.addTag(TagBuilder.buildDefaultCoreTag());
        assertTrue(uniqueTagList.contains(TagBuilder.buildDefaultCoreTag()));
    }

    @Test
    public void containsTagWithName_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.containsTagWithName(null));
    }

    @Test
    public void containsTagWithName_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.containsTagWithName("Completed"));
    }

    @Test
    public void containsTagWithName_tagInList_returnsTrue() {
        uniqueTagList.addTag(TagBuilder.buildDefaultCoreTag());
        assertTrue(uniqueTagList.containsTagWithName("Core"));
        uniqueTagList.addTag(TagBuilder.buildTestUserTag());
        assertTrue(uniqueTagList.containsTagWithName("testUserTag"));
    }

    @Test
    public void add_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.addTag(null));
    }

    @Test
    public void add_duplicateTag_throwsDuplicateTagException() {
        uniqueTagList.addTag(TagBuilder.buildDefaultCoreTag());
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.addTag(TagBuilder.buildDefaultCoreTag()));
    }

    @Test
    public void setUserTag_nullTargetTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueTagList.setUserTag(null, TagBuilder.buildTestUserTag()));
    }

    @Test
    public void setUserTag_nullEditedTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueTagList.setUserTag(TagBuilder.buildTestUserTag(), null));
    }

    @Test
    public void setUserTag_targetTagNotInList_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () ->
                uniqueTagList.setUserTag(TagBuilder.buildUserTag("test"),
                        TagBuilder.buildUserTag("other")));
    }

    @Test
    public void setUserTag_editedUserTagIsSameUserTag_success() {
        UserTag userTag = TagBuilder.buildTestUserTag();
        uniqueTagList.addTag(userTag);
        uniqueTagList.setUserTag(userTag, userTag);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.addTag(userTag);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setUserTag_editedUserTagHasSameName_success() {
        UserTag test = TagBuilder.buildUserTag("test");
        uniqueTagList.addTag(test);
        UserTag other = TagBuilder.buildUserTag("test");
        uniqueTagList.setUserTag(test, other);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.addTag(other);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setUserTag_editedUserTagHasDifferentName_success() {
        UserTag test = TagBuilder.buildUserTag("test");
        uniqueTagList.addTag(test);
        UserTag other = TagBuilder.buildUserTag("other");
        uniqueTagList.setUserTag(test, other);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.addTag(other);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void remove_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.remove(null));
    }

    @Test
    public void remove_UserTagDoesNotExist_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.remove(TagBuilder.buildTestUserTag()));
    }

    @Test
    public void remove_DefaultTag_throwsInvalidTagModificationException() {
        DefaultTag defaultTag = TagBuilder.buildDefaultCoreTag();
        uniqueTagList.addTag(defaultTag);
        assertThrows(InvalidTagModificationException.class, () -> uniqueTagList.remove(defaultTag));
    }

    @Test
    public void remove_existingUserTag_removesUserTag() {
        UserTag userTag = TagBuilder.buildTestUserTag();
        uniqueTagList.addTag(userTag);
        uniqueTagList.remove(userTag);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_nullUniqueTagList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTags((UniqueTagList) null));
    }

    @Test
    public void setTags_uniqueTagList_replacesOwnListWithProvidedUniqueTagList() {
        uniqueTagList.addTag(TagBuilder.buildTestUserTag());
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.addTag(TagBuilder.buildUserTag("other"));
        uniqueTagList.setTags(expectedUniqueTagList);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTags((List<Tag>) null));
    }

    @Test
    public void setTags_list_replacesOwnListWithProvidedList() {
        uniqueTagList.addTag(TagBuilder.buildTestUserTag());
        uniqueTagList.addTag(TagBuilder.buildDefaultCoreTag());
        Tag other = TagBuilder.buildUserTag("other");
        List<Tag> tagList = Collections.singletonList(other);
        uniqueTagList.setTags(tagList);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.addTag(other);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_listWithDuplicateUserTags_throwsDuplicateTagException() {
        UserTag userTag = TagBuilder.buildTestUserTag();
        List<Tag> listWithDuplicateUserTags = Arrays.asList(userTag, userTag);
        assertThrows(DuplicateTagException.class,
                () -> uniqueTagList.setTags(listWithDuplicateUserTags));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class,
                () -> uniqueTagList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void getDefaultTag_validDefaultTagName_success() {
        DefaultTag defaultTag = TagBuilder.buildDefaultCoreTag();
        uniqueTagList.addTag(defaultTag);
        assertEquals(defaultTag, uniqueTagList.getDefaultTag("Core"));
    }

    @Test
    public void getDefaultTag_invalidDefaultTagName_throwsTagNotFoundException() {
        uniqueTagList.initDefaultTags();
        uniqueTagList.addTag(TagBuilder.buildUserTag("exchange"));
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.getDefaultTag("exchange"));
    }

    @Test
    public void getTag_validTagName_success() {
        UserTag userTag = TagBuilder.buildTestUserTag();
        uniqueTagList.addTag(userTag);
        assertEquals(userTag, uniqueTagList.getTag("testUserTag"));

        DefaultTag defaultTag = TagBuilder.buildDefaultCoreTag();
        uniqueTagList.addTag(defaultTag);
        assertEquals(defaultTag, uniqueTagList.getTag("Core"));
    }

    @Test
    public void getTag_invalidTagName_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.getTag("interested"));
    }

    @Test
    public void equals() {
        // no tags in both lists
        UniqueTagList otherUniqueTagList = new UniqueTagList();
        assertTrue(uniqueTagList.equals(otherUniqueTagList));

        // same tag objects in both lists
        DefaultTag defaultTag = TagBuilder.buildDefaultCoreTag();
        UserTag userTag = TagBuilder.buildTestUserTag();
        otherUniqueTagList.addTag(defaultTag);
        otherUniqueTagList.addTag(userTag);
        uniqueTagList.addTag(defaultTag);
        uniqueTagList.addTag(userTag);
        assertTrue(uniqueTagList.equals(otherUniqueTagList));

        // tags with same names in both lists
        DefaultTag otherDefaultTag = TagBuilder.buildDefaultTag(DefaultTagType.COMPLETED);
        UserTag otherUserTag = TagBuilder.buildUserTag("otherUserTag");
        uniqueTagList.addTag(otherUserTag);
        uniqueTagList.addTag(otherDefaultTag);
        assertFalse(uniqueTagList.equals(otherUniqueTagList));
    }

    @Test
    public void initDefaultTags_success() {
        uniqueTagList.initDefaultTags();
        for (DefaultTagType defaultTagType: DefaultTagType.values()) {
            assertTrue(uniqueTagList.contains(TagBuilder.buildDefaultTag(defaultTagType)));
        }
    }

}
