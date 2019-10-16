//@@author shutiny-reused

package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FlashCardBuilder;

public class QuestionOrAnswerContainsAnyKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionOrAnswerContainsAnyKeywordsPredicate firstPredicate =
                new QuestionOrAnswerContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        QuestionOrAnswerContainsAnyKeywordsPredicate secondPredicate =
                new QuestionOrAnswerContainsAnyKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QuestionOrAnswerContainsAnyKeywordsPredicate firstPredicateCopy =
                new QuestionOrAnswerContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different flashCard -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_questionOrAnswerContainsKeywords_returnsTrue() {
        // One keyword in question
        QuestionOrAnswerContainsAnyKeywordsPredicate predicate =
                new QuestionOrAnswerContainsAnyKeywordsPredicate(Collections.singletonList("remainder"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // one keyword in answer
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // one keyword in both question and answer
        assertTrue(predicate.test(new FlashCardBuilder()
                .withQuestion("remainder quotient")
                .withAnswer("remainder quotient")
                .build()));

        // Multiple keywords in question
        predicate = new QuestionOrAnswerContainsAnyKeywordsPredicate(Arrays.asList("remainder", "quotient"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Multiple keywords in answer
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // Multiple keywords in both question and answer
        assertTrue(predicate.test(
                new FlashCardBuilder()
                        .withAnswer("remainder quotient")
                        .withQuestion("remainder quotient")
                        .build()));

        // Only one matching keyword in question
        predicate = new QuestionOrAnswerContainsAnyKeywordsPredicate(Arrays.asList("remainder", "divide"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Only one matching keyword in answer
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // Mixed-case keywords in question
        predicate = new QuestionOrAnswerContainsAnyKeywordsPredicate(Arrays.asList("reMaInDeR", "QuoTiEnT"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Mixed-case keywords in answer
        assertTrue(predicate.test(new FlashCardBuilder().withAnswer("remainder quotient").build()));

        // Mixed-case keywords in both question and answer
        assertTrue(predicate.test(new FlashCardBuilder()
                .withQuestion("remainder quotient")
                .withAnswer("remainder quotient").build()));
    }

    @Test
    public void test_questionOrAnswerDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        QuestionOrAnswerContainsAnyKeywordsPredicate predicate =
                new QuestionOrAnswerContainsAnyKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FlashCardBuilder().withQuestion("remainder").build()));
        assertFalse(predicate.test(new FlashCardBuilder().withAnswer("remainder").build()));


        // Non-matching keyword in question and answer
        predicate = new QuestionOrAnswerContainsAnyKeywordsPredicate(Arrays.asList("addition"));
        assertFalse(predicate.test(new FlashCardBuilder()
                .withQuestion("remainder quotient")
                .withAnswer("re")
                .build()));

        // Keywords match  rating, but does not match question and answer
        predicate = new QuestionOrAnswerContainsAnyKeywordsPredicate(Arrays.asList("12345", "Main", "good"));
        assertFalse(predicate.test(
                new FlashCardBuilder()
                        .withQuestion("addition")
                        .withAnswer("1235")
                        .withRating("good").build()));
    }
}
