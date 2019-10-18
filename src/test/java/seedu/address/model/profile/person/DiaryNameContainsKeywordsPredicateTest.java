package seedu.address.model.profile.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.diary.components.DiaryNameContainsKeywordsPredicate;
import seedu.address.testutil.diary.DiaryBuilder;

public class DiaryNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DiaryNameContainsKeywordsPredicate firstPredicate =
                new DiaryNameContainsKeywordsPredicate(firstPredicateKeywordList);
        DiaryNameContainsKeywordsPredicate secondPredicate =
                new DiaryNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DiaryNameContainsKeywordsPredicate firstPredicateCopy =
                new DiaryNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_diaryNameContainsKeywords_returnsTrue() {
        // One keyword
        DiaryNameContainsKeywordsPredicate predicate =
                new DiaryNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new DiaryBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new DiaryNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new DiaryBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new DiaryNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new DiaryBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new DiaryNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new DiaryBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_diaryNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DiaryNameContainsKeywordsPredicate predicate = new DiaryNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new DiaryBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new DiaryNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new DiaryBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new DiaryNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new DiaryBuilder().withName("Alice").build()));
    }
}
