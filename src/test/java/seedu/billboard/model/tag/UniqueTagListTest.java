package seedu.billboard.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.tag.exceptions.DuplicateTagException;
import seedu.billboard.model.tag.exceptions.TagNotFoundException;

public class UniqueTagListTest {
    private final UniqueTagList uniqueTagList = new UniqueTagList();

    @BeforeEach
    public void initialize() {
        uniqueTagList.setTagList(new HashMap<>());
    }

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.contains(null));
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.contains("ALICE"));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        uniqueTagList.add("ALICE");
        assertTrue(uniqueTagList.contains("ALICE"));
    }

    @Test
    public void add_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.add(null));
    }

    @Test
    public void add_existingTag_throwsDuplicateTagException() {
        Map<String, Tag> tags = new HashMap<>();
        tags.put("test", new Tag("test"));
        uniqueTagList.setTagList(tags);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.add("test"));
    }

    @Test
    public void addNewTags_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.addNewTags(null));
    }

    @Test
    public void retrieve_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.retrieveTag(null));
    }

    @Test
    public void retrieve_newTag_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.retrieveTag("test"));
    }

    @Test
    public void remove_newTag_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.remove("test"));
    }

    @Test
    public void retrieve_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.retrieveTags(null));
    }

    @Test
    public void retrieve_tagsNotInList_success() {
        Set<Tag> expectedSet = new HashSet<>();
        expectedSet.add(new Tag("school"));
        assertEquals(expectedSet, uniqueTagList.retrieveTags(Collections.singletonList("school")));
    }

    @Test
    public void retrieve_tagsInList_success() {
        Set<Tag> expectedSet = new HashSet<>();
        expectedSet.add(new Tag("new"));
        uniqueTagList.add("new");
        assertEquals(expectedSet, uniqueTagList.retrieveTags(Collections.singletonList("new")));
    }

    @Test
    public void remove_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.remove(null));
    }

    @Test
    public void remove_tagInList_success() {
        uniqueTagList.add("dinner");
        uniqueTagList.remove("dinner");
        assertFalse(uniqueTagList.contains("dinner"));
    }

    @Test
    public void removeAll_tagsInList_success() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("lunch");
        tagNames.add("drink");
        uniqueTagList.addNewTags(tagNames);
        List<Tag> tags = tagNames.stream().map(Tag::new).collect(Collectors.toList());
        uniqueTagList.removeAll(tags);
        assertFalse(uniqueTagList.contains("lunch"));
        assertFalse(uniqueTagList.contains("drink"));
    }

    @Test
    public void removeAll_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.removeAll(null));
    }

    @Test
    public void setList_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTagList(null));
    }

    @Test
    public void setList_success() {
        Map<String, Tag> expected = new HashMap<>();
        expected.put("test", new Tag("test"));
        UniqueTagList actual = new UniqueTagList();
        actual.setTagList(expected);
        assertEquals(expected, actual.getTagList());
    }

    @Test
    public void getTagNames_success() {
        UniqueTagList tagList = new UniqueTagList();
        List<String> names = new ArrayList<>();
        names.add("hello");
        names.add("bye");
        Collections.sort(names);
        tagList.addNewTags(names);
        assertEquals(names, tagList.getTagNames());
    }

    @Test
    public void equals_test() {
        UniqueTagList empty = new UniqueTagList();
        UniqueTagList oneTag = new UniqueTagList();
        oneTag.add("test10");
        UniqueTagList oneTagCopy = new UniqueTagList();
        oneTagCopy.add("test10");
        assertNotEquals(empty, oneTag);
        assertEquals(oneTag, oneTagCopy);
    }
}
