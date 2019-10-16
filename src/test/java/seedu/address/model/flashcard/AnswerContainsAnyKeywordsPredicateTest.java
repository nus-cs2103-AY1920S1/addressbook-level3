package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FlashCardBuilder;

public class AnswerContainsAnyKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AnswerContainsAnyKeywordsPredicate firstPredicate =
                new AnswerContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        AnswerContainsAnyKeywordsPredicate secondPredicate =
                new AnswerContainsAnyKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AnswerContainsAnyKeywordsPredicate firstPredicateCopy =
                new AnswerContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different flashCard -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_answerContainsKeywords_returnsTrue() {
        // One keyword
        AnswerContainsAnyKeywordsPredicate predicate =
                new AnswerContainsAnyKeywordsPredicate(Collections.singletonList("remainder"));
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // Multiple keywords
        predicate = new AnswerContainsAnyKeywordsPredicate(Arrays.asList("remainder", "quotient"));
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // Only one matching keyword
        predicate = new AnswerContainsAnyKeywordsPredicate(Arrays.asList("remainder", "divide"));
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // Mixed-case keywords
        predicate = new AnswerContainsAnyKeywordsPredicate(Arrays.asList("reMaInDeR", "QuoTiEnT"));
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));
    }

    @Test
    public void test_answerDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AnswerContainsAnyKeywordsPredicate predicate =
                new AnswerContainsAnyKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FlashCardBuilder().withAnswer("remainder").build()));

        // Non-matching keyword
        predicate = new AnswerContainsAnyKeywordsPredicate(Arrays.asList("addition"));
        assertFalse(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // Keywords match answer and rating, but does not match Answer
        predicate = new AnswerContainsAnyKeywordsPredicate(Arrays.asList("12345", "Main", "good"));
        assertFalse(predicate.test(
                new FlashCardBuilder()
                        .withQuestion("addition")
                        .withAnswer("1234")
                        .withRating("good").build()));
    }

}
