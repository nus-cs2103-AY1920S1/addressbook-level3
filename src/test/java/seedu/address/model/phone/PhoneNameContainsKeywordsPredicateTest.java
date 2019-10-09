package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PhoneBuilder;

class PhoneNameContainsKeywordsPredicateTest {

    @Test
    void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneNameContainsKeywordsPredicate firstPredicate =
                new PhoneNameContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneNameContainsKeywordsPredicate secondPredicate =
                new PhoneNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneNameContainsKeywordsPredicate firstPredicateCopy =
                new PhoneNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different phone -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PhoneNameContainsKeywordsPredicate predicate =
                new PhoneNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PhoneBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PhoneNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PhoneBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PhoneNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PhoneBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PhoneNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PhoneBuilder().withName("Alice Bob").build()));
    }

    @Test
    void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneNameContainsKeywordsPredicate predicate = new PhoneNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PhoneBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PhoneNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PhoneBuilder().withName("Alice Bob").build()));

        // Keywords match brand and colour, but does not match name
        predicate = new PhoneNameContainsKeywordsPredicate(Arrays.asList("12345678", "iPhone", "Blue"));
        assertFalse(predicate.test(new PhoneBuilder().withName("Alice")
                .withBrand("iPhone").withColour("Blue").build()));
    }

}
