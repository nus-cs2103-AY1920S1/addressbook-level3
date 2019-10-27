package seedu.billboard.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.tag.exceptions.DuplicateTagException;
import seedu.billboard.model.tag.exceptions.TagNotFoundException;

public class TagCountManagerTest {
    private final TagCountManager count = new TagCountManager();

    @BeforeEach
    public void initialize() {
        count.setCountMap(new HashMap<>());
    }

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.contains(null));
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        Assertions.assertFalse(count.contains(new Tag("ALICE")));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        count.add(new Tag("ALICE"));
        assertTrue(count.contains(new Tag("ALICE")));
    }

    @Test
    public void add_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.add(null));
    }

    @Test
    public void add_existingTag_throwsDuplicateTagException() {
        Map<Tag, Integer> tags = new HashMap<Tag, Integer>();
        tags.put(new Tag("test"), 1);
        count.setCountMap(tags);
        assertThrows(DuplicateTagException.class, () -> count.add(new Tag("test")));
    }

    @Test
    public void add_newTag_success() {
        count.add(new Tag("test"));
        assertTrue(count.contains(new Tag("test")));
    }

    @Test
    public void addNewTags_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.addNewTags(null));
    }

    @Test
    public void addNewTags_success() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("test2"));
        tags.add(new Tag("test3"));
        count.addNewTags(tags);
        assertTrue(count.contains(new Tag("test2")));
        assertTrue(count.contains(new Tag("test3")));
    }

    @Test
    public void incrementCount_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.incrementCount(null));
    }

    @Test
    public void increment_newTag_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> count.incrementCount(new Tag("test")));
    }

    @Test
    public void incrementCount_success() {
        Map<Tag, Integer> expected = new HashMap<>();
        Tag drinks = new Tag("drinks");
        expected.put(drinks, 0);
        count.setCountMap(expected);
        expected.replace(drinks, 1);
        count.incrementCount(drinks);
        assertEquals(expected, count.getCountMap());
    }

    @Test
    public void incrementAllCount_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.incrementAllCount(null));
    }

    @Test
    public void incrementAllCount_tagsInList_success() {
        Map<Tag, Integer> expected = new HashMap<>();
        Tag drinks = new Tag("drinks");
        Tag food = new Tag("food");
        expected.put(drinks, 0);
        expected.put(food, 0);
        count.setCountMap(expected);
        expected.replace(drinks, 1);
        expected.replace(food, 1);
        Set<Tag> tags = new HashSet<>();
        tags.add(drinks);
        tags.add(food);
        count.incrementAllCount(tags);
        assertEquals(expected, count.getCountMap());
    }

    @Test
    public void incrementAllCount_tagsNotInList_success() {
        Map<Tag, Integer> expected = new HashMap<>();
        Tag drinks = new Tag("drinks");
        Tag food = new Tag("food");
        expected.put(drinks, 0);
        count.setCountMap(expected);
        expected.replace(drinks, 1);
        expected.put(food, 1);
        Set<Tag> tags = new HashSet<>();
        tags.add(drinks);
        tags.add(food);
        count.incrementAllCount(tags);
        assertEquals(expected, count.getCountMap());
    }

    @Test
    public void decreaseCount_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.decreaseCount(null));
    }

    @Test
    public void decreaseCount_newTag_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> count.decreaseCount(new Tag("test")));
    }

    @Test
    public void decreaseCount_success() {
        Map<Tag, Integer> expected = new HashMap<>();
        Tag drinks = new Tag("drinks");
        expected.put(drinks, 1);
        count.setCountMap(expected);
        expected.replace(drinks, 0);
        count.decreaseCount(drinks);
        assertEquals(expected, count.getCountMap());
    }

    @Test
    public void decreaseAllCount_success() {
        Map<Tag, Integer> expected = new HashMap<>();
        Tag drinks = new Tag("drinks");
        Tag food = new Tag("food");
        expected.put(drinks, 1);
        expected.put(food, 1);
        count.setCountMap(expected);
        expected.replace(drinks, 0);
        expected.replace(food, 0);
        Set<Tag> tags = new HashSet<>();
        tags.add(drinks);
        tags.add(food);
        count.decreaseAllCount(tags);
        assertEquals(expected, count.getCountMap());
    }

    @Test
    public void removeZeroCount_success() {
        Map<Tag, Integer> expected = new HashMap<>();
        Tag drinks = new Tag("drinks");
        Tag food = new Tag("food");
        expected.put(drinks, 1);
        expected.put(food, 0);
        count.setCountMap(expected);
        expected.remove(food);
        count.removeZeroCount();
        assertEquals(expected, count.getCountMap());
    }

    @Test
    public void setCount_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.setCountMap(null));
    }

    @Test
    public void setCount_success() {
        Map<Tag, Integer> expected = new HashMap<>();
        expected.put(new Tag("test"), 10);
        count.setCountMap(expected);
        assertEquals(expected, count.getCountMap());
    }

    @Test
    public void equals_test() {
        TagCountManager empty = new TagCountManager();
        TagCountManager oneTag = new TagCountManager();
        oneTag.add(new Tag("test10"));
        TagCountManager oneTagCopy = new TagCountManager();
        oneTagCopy.add(new Tag("test10"));
        assertNotEquals(empty, oneTag);
        assertEquals(oneTag, oneTagCopy);
    }
}
