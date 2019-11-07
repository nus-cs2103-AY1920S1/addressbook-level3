package unrealunity.visit.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import unrealunity.visit.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeywordList = "first";
        String secondPredicateKeywordList = "first second";

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
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
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate("Alice Bob");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only partial keyword
        predicate = new NameContainsKeywordsPredicate("arol");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate("aLIce bOB");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Whitespace between keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Alice  Bob");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, but does not match name
        predicate = new NameContainsKeywordsPredicate("12345");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keywords match email, but does not match name
        predicate = new NameContainsKeywordsPredicate("alice@email.com");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keywords match address, but does not match name
        predicate = new NameContainsKeywordsPredicate("Main Street");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
