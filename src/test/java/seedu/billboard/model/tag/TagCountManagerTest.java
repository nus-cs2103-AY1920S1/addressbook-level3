package seedu.billboard.model.tag;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagCountManagerTest {
    private final TagCountManager count = new TagCountManager();

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
    public void addNewTags_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.addNewTags(null));
    }

    @Test
    public void incrementCount_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.incrementCount(null));
    }

    @Test
    public void incrementAllCount_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.incrementAllCount(null));
    }



}
