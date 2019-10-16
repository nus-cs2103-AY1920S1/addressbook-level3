package seedu.address.model.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.customer.predicates.CustomerNameContainsKeywordsPredicate;
import seedu.address.testutil.CustomerBuilder;

public class CustomerNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CustomerNameContainsKeywordsPredicate firstPredicate =
                new CustomerNameContainsKeywordsPredicate(firstPredicateKeywordList);
        CustomerNameContainsKeywordsPredicate secondPredicate =
                new CustomerNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CustomerNameContainsKeywordsPredicate firstPredicateCopy =
                new CustomerNameContainsKeywordsPredicate(firstPredicateKeywordList);
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
        CustomerNameContainsKeywordsPredicate predicate =
                new CustomerNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new CustomerNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new CustomerNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new CustomerBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new CustomerNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CustomerNameContainsKeywordsPredicate predicate =
                new CustomerNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new CustomerNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));

        // Keywords match phone and email, but does not match name
        predicate =
                new CustomerNameContainsKeywordsPredicate(
                        Arrays.asList("12345678", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice").withContactNumber("12345678")
                .withEmail("alice@email.com").build()));
    }
}
