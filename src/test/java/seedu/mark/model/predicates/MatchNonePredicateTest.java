package seedu.mark.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.testutil.BookmarkBuilder;

/**
 * Integration test with {@code NameContainsKeywordsPredicate} and {@code UrlContainsKeywordsPredicate}.
 */
public class MatchNonePredicateTest {

    private static final Bookmark SAMPLE_BOOKMARK =
            new BookmarkBuilder().withName("My First Website").withUrl("http://google.com").build();

    private static final List<String> firstKeywordsList = Arrays.asList("google");
    private static final List<String> secondKeywordsList = Arrays.asList("first", "second");

    private static final Predicate<Bookmark> PREDICATE_NAME_MATCH =
            new NameContainsKeywordsPredicate(secondKeywordsList);
    private static final Predicate<Bookmark> PREDICATE_URL_MATCH =
            new UrlContainsKeywordsPredicate(firstKeywordsList);
    private static final Predicate<Bookmark> PREDICATE_NAME_NO_MATCH =
            new NameContainsKeywordsPredicate(firstKeywordsList);
    private static final Predicate<Bookmark> PREDICATE_URL_NO_MATCH =
            new UrlContainsKeywordsPredicate(secondKeywordsList);

    @Test
    public void equals() {
        MatchNonePredicate firstPredicate = new MatchNonePredicate(Arrays.asList(PREDICATE_NAME_MATCH));
        MatchNonePredicate secondPredicate = new MatchNonePredicate(
                Arrays.asList(PREDICATE_NAME_MATCH, PREDICATE_URL_MATCH));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatchNonePredicate firstPredicateCopy = new MatchNonePredicate(Arrays.asList(PREDICATE_NAME_MATCH));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different conditions -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_noPredicatesMatch_returnsTrue() {
        // Zero predicates
        MatchNonePredicate predicate = new MatchNonePredicate(Collections.emptyList());
        assertTrue(predicate.test(SAMPLE_BOOKMARK));

        // One predicate
        predicate = new MatchNonePredicate(Arrays.asList(PREDICATE_NAME_NO_MATCH));
        assertTrue(predicate.test(SAMPLE_BOOKMARK));

        // Multiple predicates
        predicate = new MatchNonePredicate(Arrays.asList(PREDICATE_NAME_NO_MATCH, PREDICATE_URL_NO_MATCH));
        assertTrue(predicate.test(SAMPLE_BOOKMARK));
    }

    @Test
    public void test_predicatesMatch_returnsFalse() {
        // One predicate
        MatchNonePredicate predicate = new MatchNonePredicate(Arrays.asList(PREDICATE_NAME_MATCH));
        assertFalse(predicate.test(SAMPLE_BOOKMARK));

        // Multiple predicates - some match
        predicate = new MatchNonePredicate(Arrays.asList(PREDICATE_URL_NO_MATCH, PREDICATE_NAME_MATCH));
        assertFalse(predicate.test(SAMPLE_BOOKMARK));

        // Multiple predicates - all match
        predicate = new MatchNonePredicate(Arrays.asList(PREDICATE_NAME_MATCH, PREDICATE_URL_MATCH));
        assertFalse(predicate.test(SAMPLE_BOOKMARK));
    }
}
