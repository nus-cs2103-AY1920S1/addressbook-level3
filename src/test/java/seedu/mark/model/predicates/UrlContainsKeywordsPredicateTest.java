package seedu.mark.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.testutil.BookmarkBuilder;

public class UrlContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        UrlContainsKeywordsPredicate firstPredicate = new UrlContainsKeywordsPredicate(firstPredicateKeywordList);
        UrlContainsKeywordsPredicate secondPredicate = new UrlContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UrlContainsKeywordsPredicate firstPredicateCopy = new UrlContainsKeywordsPredicate(firstPredicateKeywordList);
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
        // One keyword
        UrlContainsKeywordsPredicate predicate = new UrlContainsKeywordsPredicate(Collections.singletonList("google"));
        assertTrue(predicate.test(new BookmarkBuilder().withUrl("http://google.com").build()));

        // Multiple keywords
        predicate = new UrlContainsKeywordsPredicate(Arrays.asList("http", "google"));
        assertTrue(predicate.test(new BookmarkBuilder().withUrl("http://google.com").build()));

        // Only one matching keyword
        predicate = new UrlContainsKeywordsPredicate(Arrays.asList("https", "google"));
        assertTrue(predicate.test(new BookmarkBuilder().withUrl("http://google.com").build()));

        // Mixed-case keywords
        predicate = new UrlContainsKeywordsPredicate(Arrays.asList("hTTp", "GoOgLe"));
        assertTrue(predicate.test(new BookmarkBuilder().withUrl("http://google.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        UrlContainsKeywordsPredicate predicate = new UrlContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BookmarkBuilder().withUrl("http://google.com").build()));

        // Non-matching keyword
        predicate = new UrlContainsKeywordsPredicate(Collections.singletonList("Facebook"));
        assertFalse(predicate.test(new BookmarkBuilder().withUrl("http://google.com").build()));

        // Keywords match remark and name, but do not match url
        predicate = new UrlContainsKeywordsPredicate(Arrays.asList("Alice", "Main", "Street"));
        assertFalse(predicate.test(new BookmarkBuilder().withName("Alice")
                .withUrl("https://traveller-gmail.com").withRemark("Main Street").build()));
    }
}
