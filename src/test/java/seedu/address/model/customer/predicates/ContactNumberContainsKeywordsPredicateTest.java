package seedu.address.model.customer.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

public class ContactNumberContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("98237102");
        List<String> secondPredicateKeywordList = Arrays.asList("98237102", "99123892");

        ContactNumberContainsKeywordsPredicate firstPredicate =
                new ContactNumberContainsKeywordsPredicate(firstPredicateKeywordList);
        ContactNumberContainsKeywordsPredicate secondPredicate =
                new ContactNumberContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactNumberContainsKeywordsPredicate firstPredicateCopy =
                new ContactNumberContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate-> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactNumberContainsKeywords_returnsTrue() {
        // exact match
        ContactNumberContainsKeywordsPredicate predicate =
                new ContactNumberContainsKeywordsPredicate(Collections.singletonList("98237102"));
        assertTrue(predicate.test(new CustomerBuilder().withContactNumber("98237102").build()));

        // Multiple keywords
        predicate = new ContactNumberContainsKeywordsPredicate(Arrays.asList("98237102", "99123892"));
        assertTrue(predicate.test(new CustomerBuilder().withContactNumber("98237102").build()));

        // partial match
        predicate = new ContactNumberContainsKeywordsPredicate(Arrays.asList("982"));
        assertTrue(predicate.test(new CustomerBuilder().withContactNumber("98237102").build()));

        // 1-letter match
        predicate = new ContactNumberContainsKeywordsPredicate(Arrays.asList("9"));
        assertTrue(predicate.test(new CustomerBuilder().withContactNumber("98237102").build()));
    }

    @Test
    public void test_contactNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContactNumberContainsKeywordsPredicate predicate =
                new ContactNumberContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CustomerBuilder().withContactNumber("98237102").build()));

        // Non-matching keyword
        predicate = new ContactNumberContainsKeywordsPredicate(Arrays.asList("4"));
        assertFalse(predicate.test(new CustomerBuilder().withContactNumber("98237102").build()));

        // Keywords match name and email, but does not match contactnumber
        predicate =
                new ContactNumberContainsKeywordsPredicate(
                        Arrays.asList("alice@email.com", "Alice"));
        assertFalse(predicate.test(new CustomerBuilder().withName("Alice").withContactNumber("12345678")
                .withEmail("alice@email.com").build()));
    }
}
