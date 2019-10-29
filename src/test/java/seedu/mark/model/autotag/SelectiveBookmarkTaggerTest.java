package seedu.mark.model.autotag;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
