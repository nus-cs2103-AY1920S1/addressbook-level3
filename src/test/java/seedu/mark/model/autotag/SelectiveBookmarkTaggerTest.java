package seedu.mark.model.autotag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.NameContainsKeywordsPredicate;
import seedu.mark.model.tag.Tag;
import seedu.mark.testutil.BookmarkBuilder;

class SelectiveBookmarkTaggerTest {

    private static final Tag SAMPLE_TAG = new Tag("myTag");
    private static final Predicate<Bookmark> NAME_PREDICATE_HELLO =
            new NameContainsKeywordsPredicate(Arrays.asList("hello"));

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
