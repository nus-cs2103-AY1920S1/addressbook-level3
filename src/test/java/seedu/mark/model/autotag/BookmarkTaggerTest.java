package seedu.mark.model.autotag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.tag.Tag;
import seedu.mark.testutil.BookmarkBuilder;

class BookmarkTaggerTest {

    @Test
    public void applyTag_bookmarkDoesNotContainTag_tagAdded() {
        Bookmark originalBookmark = new BookmarkBuilder().withTags("module", "readLater").build();
        BookmarkTagger tagger = new BookmarkTagger(new Tag("NUS"));

        Bookmark expectedBookmark = new BookmarkBuilder().withTags("module", "readLater", "NUS").build();

        assertEquals(expectedBookmark, tagger.applyTag(originalBookmark));
    }

    @Test
    public void applyTag_bookmarkContainsTag_noChange() {
        Bookmark expectedBookmark = new BookmarkBuilder().withTags("module", "readLater").build();
        BookmarkTagger tagger = new BookmarkTagger(new Tag("module"));

        assertEquals(expectedBookmark, tagger.applyTag(expectedBookmark));
    }

    @Test
    public void equals() {
        // same object -> returns true
        BookmarkTagger tagger = new BookmarkTagger(new Tag("first"));
        assertTrue(tagger.equals(tagger));

        // same values -> returns true
        assertTrue(tagger.equals(new BookmarkTagger(new Tag("first"))));

        // null -> returns false
        assertFalse(tagger.equals(null));

        // different tag -> returns false
        assertFalse(tagger.equals(new BookmarkTagger(new Tag("second"))));
    }
}
