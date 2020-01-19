package seedu.mark.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSampleBookmarks_mustHaveAtLeastOneBookmark_returnsTrue() {
        assertTrue(SampleDataUtil.getSampleBookmarks().length > 0);
    }

    @Test
    public void getSampleFolderStructure_mustHaveAtLeastOneSubFolder_returnsTrue() {
        assertTrue(SampleDataUtil.getSampleFolderStructure().getSubfolders().size() > 0);
    }

    @Test
    public void getSampleMark_mustHaveData_returnsTrue() {
        ReadOnlyMark mark = SampleDataUtil.getSampleMark();
        assertTrue(mark.getBookmarkList().size() > 0);
        assertTrue(mark.getFolderStructure().getSubfolders().size() > 0);
    }

    @Test
    public void getTagSet() {
        assertEquals(SampleDataUtil.getTagSet(VALID_TAG_HUSBAND, VALID_TAG_FRIEND),
                Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));
    }

    @Test
    public void getTagger() {
        BookmarkPredicate predicate = new BookmarkPredicate();
        assertEquals(SampleDataUtil.getTagger(VALID_TAG_FRIEND, predicate),
                new SelectiveBookmarkTagger(new Tag(VALID_TAG_FRIEND), predicate));
    }
}
