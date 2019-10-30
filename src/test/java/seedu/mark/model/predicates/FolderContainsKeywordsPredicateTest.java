package seedu.mark.model.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.util.BookmarkBuilder;

public class FolderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FolderContainsKeywordsPredicate firstPredicate =
                new FolderContainsKeywordsPredicate(firstPredicateKeywordList);
        FolderContainsKeywordsPredicate secondPredicate =
                new FolderContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FolderContainsKeywordsPredicate firstPredicateCopy =
                new FolderContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different bookmark -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_folderContainsKeywords_returnsTrue() {
        // One keyword
        FolderContainsKeywordsPredicate predicate = new FolderContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new BookmarkBuilder().withFolder("Alice").build()));

        // Multiple keywords
        predicate = new FolderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new BookmarkBuilder().withFolder("Alice").build()));

        // Only one matching keyword
        predicate = new FolderContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new BookmarkBuilder().withFolder("Carol").build()));

        // Mixed-case keywords
        predicate = new FolderContainsKeywordsPredicate(Arrays.asList("aLIce"));
        assertTrue(predicate.test(new BookmarkBuilder().withFolder("Alice").build()));
    }

    @Test
    public void test_folderDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FolderContainsKeywordsPredicate predicate = new FolderContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BookmarkBuilder().withFolder("Alice").build()));

        // Non-matching keyword
        predicate = new FolderContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BookmarkBuilder().withFolder("AliceBob").build()));

        // Keywords match name and url, but does not match folder
        predicate = new FolderContainsKeywordsPredicate(Arrays.asList("https://www.example.com", "Example", "Folder"));
        assertFalse(predicate.test(new BookmarkBuilder().withName("Example")
                .withUrl("https://www.example.com").withFolder("DifferentFolder").build()));

        // Partially matching keyword
        predicate = new FolderContainsKeywordsPredicate(Arrays.asList("al"));
        assertFalse(predicate.test(new BookmarkBuilder().withFolder("Alice").build()));
    }
}
