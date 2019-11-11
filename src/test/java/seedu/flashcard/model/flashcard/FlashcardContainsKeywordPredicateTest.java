package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.flashcard.testutil.FlashcardBuilder;

public class FlashcardContainsKeywordPredicateTest {

    @Test
    public void equalsTest() {
        List<String> firstPredicateKeyWordList = Collections.singletonList("first");
        List<String> secondPredicateKeyWordList = Arrays.asList("first", "second");
        FlashcardContainsKeywordsPredicate firstPredicate =
            new FlashcardContainsKeywordsPredicate(firstPredicateKeyWordList);
        FlashcardContainsKeywordsPredicate secondPredicate =
            new FlashcardContainsKeywordsPredicate(secondPredicateKeyWordList);
        assertTrue(firstPredicate.equals(firstPredicate));
        FlashcardContainsKeywordsPredicate firstPredicateCopy =
            new FlashcardContainsKeywordsPredicate(firstPredicateKeyWordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_flashcardContainsKeywords_returnsTrue() {
        FlashcardContainsKeywordsPredicate predicate =
            new FlashcardContainsKeywordsPredicate(Collections.singletonList("Blanc"));
        assertTrue(predicate.test(new FlashcardBuilder().withQuestion("Mount Blanc").buildShortAnswerFlashcard()));
        predicate = new FlashcardContainsKeywordsPredicate(Arrays.asList("Daxing", "Airport"));
        assertTrue(predicate.test(new FlashcardBuilder().withQuestion("Daxing Blanc").buildShortAnswerFlashcard()));
        assertTrue(predicate.test(new FlashcardBuilder().withAnswer("Haneda Airport").buildShortAnswerFlashcard()));
    }

    @Test
    public void test_flashcardDoesNotContainKeywords_returnsFalse() {
        FlashcardContainsKeywordsPredicate predicate =
            new FlashcardContainsKeywordsPredicate(Collections.singletonList("Blanc"));
        assertFalse(predicate.test(new FlashcardBuilder().withAnswer("Daxing airport").buildShortAnswerFlashcard()));
    }
}
