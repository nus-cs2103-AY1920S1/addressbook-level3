package seedu.address.model.entity.worker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.WorkerBuilder;

public class WorkerNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        WorkerNameContainsKeywordsPredicate firstPredicate =
                new WorkerNameContainsKeywordsPredicate(firstPredicateKeywordList);
        WorkerNameContainsKeywordsPredicate secondPredicate =
                new WorkerNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        WorkerNameContainsKeywordsPredicate firstPredicateCopy =
                new WorkerNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        WorkerNameContainsKeywordsPredicate predicate =
                new WorkerNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new WorkerBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new WorkerNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new WorkerBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new WorkerNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new WorkerBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new WorkerNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new WorkerBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        WorkerNameContainsKeywordsPredicate predicate =
                new WorkerNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new WorkerBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new WorkerNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new WorkerBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new WorkerNameContainsKeywordsPredicate(Arrays.asList("Bob", "mother", "homicide"));
        assertFalse(predicate.test(new WorkerBuilder().withName("Alice").withEmploymentStatus("Full-time")
                .withDesignation("Mrs").build()));
    }
}
