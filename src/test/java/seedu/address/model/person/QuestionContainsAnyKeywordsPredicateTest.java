package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class QuestionContainsAnyKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionContainsAnyKeywordsPredicate firstPredicate = new QuestionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        QuestionContainsAnyKeywordsPredicate secondPredicate = new QuestionContainsAnyKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QuestionContainsAnyKeywordsPredicate firstPredicateCopy = new QuestionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
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
        QuestionContainsAnyKeywordsPredicate predicate = new QuestionContainsAnyKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withQuestion("Alice Bob").build()));

        // Multiple keywords
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withQuestion("Alice Bob").build()));

        // Only one matching keyword
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withQuestion("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withQuestion("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        QuestionContainsAnyKeywordsPredicate predicate = new QuestionContainsAnyKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withQuestion("Alice").build()));

        // Non-matching keyword
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withQuestion("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withQuestion("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
