package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.PersonBuilder;

public class PersonNameHasKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonNameHasKeywordsPredicate firstPredicate = new PersonNameHasKeywordsPredicate(firstPredicateKeywordList);
        PersonNameHasKeywordsPredicate secondPredicate =
                new PersonNameHasKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonNameHasKeywordsPredicate firstPredicateCopy =
                new PersonNameHasKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void getKeywords_validInput_success() {
        List<String> keywordList = Arrays.asList("first", "second");
        PersonNameHasKeywordsPredicate predicate = new PersonNameHasKeywordsPredicate(keywordList);
        assertEquals(keywordList, predicate.getKeywords());
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonNameHasKeywordsPredicate predicate =
                new PersonNameHasKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonNameHasKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonNameHasKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonNameHasKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonNameHasKeywordsPredicate predicate = new PersonNameHasKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonNameHasKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, but does not match name
        predicate = new PersonNameHasKeywordsPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").build()));
    }
}
