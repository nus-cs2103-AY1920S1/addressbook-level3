package seedu.mark.model.autotag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.NameContainsKeywordsPredicate;
import seedu.mark.model.tag.Tag;

class SelectiveBookmarkTaggerTest { // TODO: write tests

    private static final Tag SAMPLE_TAG = new Tag("myTag");
    private static final Tag SAMPLE_TAG_2 = new Tag("differentTag");
    private static final Predicate<Bookmark> SAMPLE_PREDICATE =
            new NameContainsKeywordsPredicate(Arrays.asList("hello"));
    private static final Predicate<Bookmark> SAMPLE_PREDICATE_2 =
            new NameContainsKeywordsPredicate(Arrays.asList("world"));

    /*
    @Test
    public void applyTagSelectively() {
    }
    */

    @Test
    public void equals() {
        // same object -> returns true
        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(SAMPLE_TAG, SAMPLE_PREDICATE);
        assertTrue(tagger.equals(tagger));

        // same values -> returns true
        assertTrue(tagger.equals(new SelectiveBookmarkTagger(SAMPLE_TAG, SAMPLE_PREDICATE)));

        // null -> returns false
        assertFalse(tagger.equals(null));

        // different tag -> returns false
        assertFalse(tagger.equals(new SelectiveBookmarkTagger(SAMPLE_TAG_2, SAMPLE_PREDICATE)));

        // different predicate -> returns false
        assertFalse(tagger.equals(new SelectiveBookmarkTagger(SAMPLE_TAG, SAMPLE_PREDICATE_2)));
    }
}
