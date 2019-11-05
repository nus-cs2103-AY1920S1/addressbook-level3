package seedu.address.model.customer.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

public class CustomerContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CustomerContainsKeywordsPredicate firstPredicate =
                new CustomerContainsKeywordsPredicate(firstPredicateKeywordList);
        CustomerContainsKeywordsPredicate secondPredicate =
                new CustomerContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CustomerContainsKeywordsPredicate firstPredicateCopy =
                new CustomerContainsKeywordsPredicate(firstPredicateKeywordList);
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
        // One keyword that matches customer name
        CustomerContainsKeywordsPredicate predicate =
                new CustomerContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));

        //one keyword that matches email
        predicate =
                new CustomerContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));

        //one keyword that matches contact number
        predicate =
                new CustomerContainsKeywordsPredicate(Collections.singletonList("12345678"));
        assertTrue(predicate.test(new CustomerBuilder().withContactNumber("12345678").build()));

        // one keyword that matches tags
        predicate = new CustomerContainsKeywordsPredicate(Collections.singletonList("Rich"));
        assertTrue(predicate.test(new CustomerBuilder().withTags("Rich").build()));



        // Multiple keywords
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));



        // Mixed-case keywords
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        // Only one matching keyword in customer name
        CustomerContainsKeywordsPredicate predicate =
                new CustomerContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice Carol").build()));

        // Only one matching keyword in email
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
        assertFalse(predicate.test(new CustomerBuilder().withEmail("alice@gmail.com").build()));

        // Only one matching keyword in contact number
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("99817283", "27371929"));
        assertFalse(predicate.test(new CustomerBuilder().withContactNumber("99817283").build()));

        // Only one matching keyword in tags
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("Regular", "Rich"));
        assertFalse(predicate.test(new CustomerBuilder().withTags("Rich").build()));


        // Zero keywords
        predicate =
                new CustomerContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice Bob").build()));
    }
}
