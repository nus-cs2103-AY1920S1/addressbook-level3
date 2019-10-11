//@@author shutingy-reused
//original code is from NameContainsKeywordsPredicateTest.java by si jie
//reused with minor changes

package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FlashCardBuilder;

public class QuestionContainsAllKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionContainsAllKeywordsPredicate firstPredicate =
                new QuestionContainsAllKeywordsPredicate(firstPredicateKeywordList);
        QuestionContainsAllKeywordsPredicate secondPredicate =
                new QuestionContainsAllKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QuestionContainsAllKeywordsPredicate firstPredicateCopy =
                new QuestionContainsAllKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_questionContainsAllKeywords_returnsTrue() {
        // One keyword all match
        QuestionContainsAllKeywordsPredicate predicate =
                new QuestionContainsAllKeywordsPredicate(Collections.singletonList("remainder"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder").build()));

        // Multiple keywords all match
        predicate = new QuestionContainsAllKeywordsPredicate(Arrays.asList("remainder", "quotient"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Mixed-case keywords all match
        predicate = new QuestionContainsAllKeywordsPredicate(Arrays.asList("reMaInDeR", "QuoTiEnT"));
        assertTrue(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));
    }

    @Test
    public void test_questionDoesNotAllContainKeywords_returnsFalse() {
        // Non-matching keyword
        QuestionContainsAllKeywordsPredicate predicate =
                new QuestionContainsAllKeywordsPredicate(Arrays.asList("addition"));
        assertFalse(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Only one matching keyword
        predicate = new QuestionContainsAllKeywordsPredicate(Arrays.asList("remainder", "divide"));
        assertFalse(predicate.test(new FlashCardBuilder().withQuestion("remainder quotient").build()));

        // Keywords match answer and rating, but does not match question
        predicate = new QuestionContainsAllKeywordsPredicate(Arrays.asList("12345", "Main", "good"));
        assertFalse(predicate.test(
                new FlashCardBuilder()
                        .withQuestion("addition")
                        .withAnswer("12345")
                        .withRating("good").build()));
    }
}
