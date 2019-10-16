package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FlashCardBuilder;

public class QuestionContainsAnyKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionContainsAnyKeywordsPredicate firstPredicate =
                new QuestionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        QuestionContainsAnyKeywordsPredicate secondPredicate =
                new QuestionContainsAnyKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QuestionContainsAnyKeywordsPredicate firstPredicateCopy =
                new QuestionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different flashCard -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_questionContainsKeywords_returnsTrue() {
        // One keyword
        QuestionContainsAnyKeywordsPredicate predicate =
                new QuestionContainsAnyKeywordsPredicate(Collections.singletonList("remainder"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Multiple keywords
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("remainder", "quotient"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Only one matching keyword
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("remainder", "divide"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Mixed-case keywords
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("reMaInDeR", "QuoTiEnT"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));
    }

    @Test
    public void test_questionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        QuestionContainsAnyKeywordsPredicate predicate =
                new QuestionContainsAnyKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FlashCardBuilder().withQuestion("remainder").build()));

        // Non-matching keyword
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("addition"));
        assertFalse(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Keywords match answer and rating, but does not match question
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("12345", "Main", "good"));
        assertFalse(predicate.test(
                new FlashCardBuilder()
                        .withQuestion("addition")
                        .withAnswer("12345")
                        .withRating("good").build()));
    }
}
