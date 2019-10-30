package seedu.mark.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.util.BookmarkBuilder;

public class BookmarkContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        BookmarkContainsKeywordsPredicate firstPredicate = new BookmarkContainsKeywordsPredicate(
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList);
        BookmarkContainsKeywordsPredicate secondPredicate = new BookmarkContainsKeywordsPredicate(
                secondPredicateKeywordList, secondPredicateKeywordList, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookmarkContainsKeywordsPredicate firstPredicateCopy = new BookmarkContainsKeywordsPredicate(
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_bookmarkContainsKeywords_returnsTrue() {
        // Name predicate matches
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(
                Collections.singletonList("website"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new BookmarkBuilder().withName("A Website").withUrl("http://google.com").build()));

        // Url predicate matches
        predicate = new BookmarkContainsKeywordsPredicate(
                Collections.singletonList("http"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new BookmarkBuilder().withName("A Website").withUrl("http://google.com").build()));

        // Both Name and Url matches
        predicate = new BookmarkContainsKeywordsPredicate(
                Collections.singletonList("google"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(
                new BookmarkBuilder().withName("Google Website").withUrl("http://google.com").build()));

        // Tag predicate matches
        predicate = new BookmarkContainsKeywordsPredicate(
                Collections.emptyList(), Collections.singletonList("tag"), Collections.emptyList());
        assertTrue(predicate.test(new BookmarkBuilder().withTags("tag").build()));

        // Folder predicate matches
        predicate = new BookmarkContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.singletonList("folder"));
        assertTrue(predicate.test(new BookmarkBuilder().withFolder("folder").build()));

        // Multiple keywords
        predicate = new BookmarkContainsKeywordsPredicate(
                Arrays.asList("com", "google"), Collections.singletonList("tag"), Collections.singletonList("folder"));
        assertTrue(predicate.test(new BookmarkBuilder()
                .withName("Google Website").withUrl("http://google.com").withTags("tag").withFolder("folder").build()));
    }

    @Test
    public void test_bookmarkDoesNotContainKeywords_returnsFalse() {
        // Zero predicates match
        BookmarkContainsKeywordsPredicate predicate = new BookmarkContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new BookmarkBuilder().build()));
    }
}
