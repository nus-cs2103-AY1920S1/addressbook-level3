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
}
