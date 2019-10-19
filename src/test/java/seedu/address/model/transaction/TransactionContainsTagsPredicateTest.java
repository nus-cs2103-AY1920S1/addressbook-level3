package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;


public class TransactionContainsTagsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TransactionContainsTagsPredicate firstPredicate =
                new TransactionContainsTagsPredicate(firstPredicateKeywordList);
        TransactionContainsTagsPredicate secondPredicate =
                new TransactionContainsTagsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TransactionContainsTagsPredicate firstPredicateCopy =
                new TransactionContainsTagsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TransactionContainsTagsPredicate predicate =
                new TransactionContainsTagsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new TransactionBuilder().withTags("Alice", "Bob").build()));

        // Multiple keywords
        predicate = new TransactionContainsTagsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new TransactionBuilder().withTags("Alice", "Bob").build()));

        // Only one matching keyword
        predicate = new TransactionContainsTagsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new TransactionBuilder().withTags("Alice", "Carol").build()));

        // Mixed-case keywords
        predicate = new TransactionContainsTagsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new TransactionBuilder().withTags("Alice", "Bob").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionContainsTagsPredicate predicate = new TransactionContainsTagsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withTags("Alice").build()));

        // Non-matching keyword
        predicate = new TransactionContainsTagsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new TransactionBuilder().withTags("Alice", "Bob").build()));

        // Keywords match amount and date, but does not match tag
        predicate = new TransactionContainsTagsPredicate(Arrays.asList("12345", "1"));
        assertFalse(predicate.test(new TransactionBuilder().withTags("Alice").withAmount("12345")
                .withDate("1").build()));
    }

}
