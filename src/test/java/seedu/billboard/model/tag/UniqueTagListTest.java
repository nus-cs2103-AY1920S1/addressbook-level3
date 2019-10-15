package seedu.billboard.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UniqueTagListTest {
    private final UniqueTagList uniqueTagList = new UniqueTagList();

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.contains(null));
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        Assertions.assertFalse(uniqueTagList.contains("ALICE"));
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
    public void retrieve_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.retrieveTag(null));
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
}
