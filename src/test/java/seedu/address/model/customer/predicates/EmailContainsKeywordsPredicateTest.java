package seedu.address.model.customer.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("alice");
        List<String> secondPredicateKeywordList = Arrays.asList("alice", "bob");

        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // exact match
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@gmail.com"));
        assertTrue(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
        assertTrue(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));

        // partial match
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("al", "bob"));
        assertTrue(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("aL", "bOB"));
        assertTrue(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("carol"));
        assertFalse(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));

        // Keywords match phone, but does not match email
        predicate =
                new EmailContainsKeywordsPredicate(
                        Arrays.asList("12345678"));
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice").withContactNumber("12345678")
                .withEmail("alice@email.com").build()));
    }
}
