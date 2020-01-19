package seedu.mark.model.autotag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;

class SelectiveBookmarkTaggerTest {

    private static final Tag SAMPLE_TAG = new Tag("myTag");
    private static final BookmarkPredicate NAME_PREDICATE_HELLO =
            new BookmarkPredicate().withNameKeywords(Arrays.asList("hello"));


    @Test
    public void hasEmptyPredicate() {
        assertTrue(new SelectiveBookmarkTagger(SAMPLE_TAG, new BookmarkPredicate()).hasEmptyPredicate());
        assertFalse(new SelectiveBookmarkTagger(SAMPLE_TAG, NAME_PREDICATE_HELLO).hasEmptyPredicate());
    }


    @Test
    public void equals() {
        // same values -> returns true
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(SAMPLE_TAG, NAME_PREDICATE_HELLO);
        assertTrue(tagger.equals(new SelectiveBookmarkTagger(SAMPLE_TAG, NAME_PREDICATE_HELLO)));

        // different tag -> returns false
        assertFalse(tagger.equals(new SelectiveBookmarkTagger(new Tag("Random"), NAME_PREDICATE_HELLO)));

        // different predicate -> returns false
        assertFalse(tagger.equals(new SelectiveBookmarkTagger(SAMPLE_TAG, new BookmarkPredicate())));
    }

    @Test
    public void applyTagSelectively_bookmarkMatches_returnTaggedBookmark() {
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(SAMPLE_TAG, NAME_PREDICATE_HELLO);
        Bookmark initialBookmark = new BookmarkBuilder().withName("Hello World").build();
        Bookmark expectedBookmark = new BookmarkBuilder().withName("Hello World").withTags(SAMPLE_TAG.tagName).build();

        assertEquals(tagger.applyTagSelectively(initialBookmark), expectedBookmark);
    }

    @Test
    public void applyTagSelectively_bookmarkDoesNotMatch_returnBookmark() {
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(SAMPLE_TAG, NAME_PREDICATE_HELLO);
        Bookmark initialBookmark = new BookmarkBuilder().withName("Google").build();
        Bookmark expectedBookmark = new BookmarkBuilder(initialBookmark).build();

        assertEquals(tagger.applyTagSelectively(initialBookmark), expectedBookmark);
    }
}
