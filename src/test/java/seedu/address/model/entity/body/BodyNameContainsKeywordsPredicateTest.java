package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BodyBuilder;

public class BodyNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        BodyNameContainsKeywordsPredicate firstPredicate =
                new BodyNameContainsKeywordsPredicate(firstPredicateKeywordList);
        BodyNameContainsKeywordsPredicate secondPredicate =
                new BodyNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BodyNameContainsKeywordsPredicate firstPredicateCopy =
                new BodyNameContainsKeywordsPredicate(firstPredicateKeywordList);
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
        BodyNameContainsKeywordsPredicate predicate =
                new BodyNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new BodyBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new BodyNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new BodyBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new BodyNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new BodyBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new BodyNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new BodyBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        BodyNameContainsKeywordsPredicate predicate = new BodyNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BodyBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new BodyNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BodyBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new BodyNameContainsKeywordsPredicate(Arrays.asList("Bob", "mother", "homicide"));
        assertFalse(predicate.test(new BodyBuilder().withName("Alice").withNextOfKin("Bob")
                .withCauseOfDeath("homicide").withRelationship("Mother").build()));
    }
}
