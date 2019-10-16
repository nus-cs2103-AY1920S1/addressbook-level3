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
public class MatchAllPredicateTest {

    private static final Bookmark SAMPLE_BOOKMARK =
            new BookmarkBuilder().withName("My First Website").withUrl("http://google.com").build();

    private static final List<String> firstKeywordsList = Arrays.asList("google");
    private static final List<String> secondKeywordsList = Arrays.asList("first", "second");

    private static final Predicate<Bookmark> PREDICATE_NAME_MATCH =
            new NameContainsKeywordsPredicate(secondKeywordsList);
    private static final Predicate<Bookmark> PREDICATE_URL_MATCH =
            new UrlContainsKeywordsPredicate(firstKeywordsList);
    private static final Predicate<Bookmark> PREDICATE_URL_MATCH_2 =
            new UrlContainsKeywordsPredicate(Arrays.asList(".com"));
    private static final Predicate<Bookmark> PREDICATE_NAME_NO_MATCH =
            new NameContainsKeywordsPredicate(firstKeywordsList);
    private static final Predicate<Bookmark> PREDICATE_URL_NO_MATCH =
            new UrlContainsKeywordsPredicate(secondKeywordsList);

    @Test
    public void equals() {
        MatchAllPredicate firstPredicate = new MatchAllPredicate(Arrays.asList(PREDICATE_NAME_MATCH));
        MatchAllPredicate secondPredicate = new MatchAllPredicate(
                Arrays.asList(PREDICATE_NAME_MATCH, PREDICATE_URL_MATCH));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MatchAllPredicate firstPredicateCopy = new MatchAllPredicate(Arrays.asList(PREDICATE_NAME_MATCH));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different conditions -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_allPredicatesMatch_returnsTrue() {
        // One predicate
        MatchAllPredicate predicate = new MatchAllPredicate(Arrays.asList(PREDICATE_NAME_MATCH));
        assertTrue(predicate.test(SAMPLE_BOOKMARK));

        // Multiple predicates, different fields
        predicate = new MatchAllPredicate(Arrays.asList(PREDICATE_NAME_MATCH, PREDICATE_URL_MATCH));
        assertTrue(predicate.test(SAMPLE_BOOKMARK));

        // Multiple predicates, same field
        predicate = new MatchAllPredicate(Arrays.asList(PREDICATE_URL_MATCH_2, PREDICATE_URL_MATCH));
        assertTrue(predicate.test(SAMPLE_BOOKMARK));
    }

    @Test
    public void test_predicatesDoNotMatch_returnsFalse() {
        // Zero predicates
        MatchAllPredicate predicate = new MatchAllPredicate(Collections.emptyList());
        assertFalse(predicate.test(SAMPLE_BOOKMARK));

        // Multiple predicates - none match
        predicate = new MatchAllPredicate(Arrays.asList(PREDICATE_NAME_NO_MATCH, PREDICATE_URL_NO_MATCH));
        assertFalse(predicate.test(SAMPLE_BOOKMARK));

        // Multiple predicates - some do not match
        predicate = new MatchAllPredicate(Arrays.asList(PREDICATE_URL_NO_MATCH, PREDICATE_NAME_MATCH));
        assertFalse(predicate.test(SAMPLE_BOOKMARK));
    }
}
