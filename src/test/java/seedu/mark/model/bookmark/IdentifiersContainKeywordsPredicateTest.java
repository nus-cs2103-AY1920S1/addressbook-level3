package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.testutil.BookmarkBuilder;

public class IdentifiersContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        IdentifiersContainKeywordsPredicate firstPredicate =
                new IdentifiersContainKeywordsPredicate(firstPredicateKeywordList);
        IdentifiersContainKeywordsPredicate secondPredicate =
                new IdentifiersContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IdentifiersContainKeywordsPredicate firstPredicateCopy =
                new IdentifiersContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_urlContainsKeywords_returnsTrue() {
        // Name predicate matches
        IdentifiersContainKeywordsPredicate predicate =
                new IdentifiersContainKeywordsPredicate(Collections.singletonList("website"));
        assertTrue(predicate.test(new BookmarkBuilder().withName("A Website").withUrl("http://google.com").build()));

        // Url predicate matches
        predicate = new IdentifiersContainKeywordsPredicate(Collections.singletonList("http"));
        assertTrue(predicate.test(new BookmarkBuilder().withName("A Website").withUrl("http://google.com").build()));

        // Multiple predicates match
        predicate = new IdentifiersContainKeywordsPredicate(Collections.singletonList("google"));
        assertTrue(predicate.test(
                new BookmarkBuilder().withName("Google Website").withUrl("http://google.com").build()));

        // Multiple keywords
        predicate = new IdentifiersContainKeywordsPredicate(Arrays.asList("com", "google"));
        assertTrue(predicate.test(
                new BookmarkBuilder().withName("Google Website").withUrl("http://google.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero predicates match
        IdentifiersContainKeywordsPredicate predicate =
                new IdentifiersContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BookmarkBuilder().withUrl("http://google.com").build()));
    }
}
