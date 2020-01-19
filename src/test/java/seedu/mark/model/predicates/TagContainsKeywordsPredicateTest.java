package seedu.mark.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.util.BookmarkBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different bookmark -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new BookmarkBuilder().withTags("Alice").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new BookmarkBuilder().withTags("Alice", "Bob").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new BookmarkBuilder().withTags("Carol").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("aLIce"));
        assertTrue(predicate.test(new BookmarkBuilder().withTags("Alice").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BookmarkBuilder().withTags("Alice").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BookmarkBuilder().withTags("AliceBob").build()));

        // Keywords match name and url, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("https://www.example.com", "Example", "Tag"));
        assertFalse(predicate.test(new BookmarkBuilder().withName("Example")
                .withUrl("https://www.example.com").withTags("DifferentTag").build()));

        // Partially matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("al"));
        assertFalse(predicate.test(new BookmarkBuilder().withTags("Alice").build()));
    }
}
