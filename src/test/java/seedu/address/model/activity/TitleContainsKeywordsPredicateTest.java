package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.TypicalActivities;

public class TitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TitleContainsKeywordsPredicate firstPredicate = new TitleContainsKeywordsPredicate(firstPredicateKeywordList);
        TitleContainsKeywordsPredicate secondPredicate = new TitleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleContainsKeywordsPredicate firstPredicateCopy = new TitleContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_titleContainsKeywords_returnsTrue() {
        // One keyword
        TitleContainsKeywordsPredicate predicate = new TitleContainsKeywordsPredicate(
                Collections.singletonList("Breakfast"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Breakfast with Keven").build()));

        // Multiple keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Breakfast", "Keven"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Breakfast with Keven").build()));

        // Only one matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Keven", "Lunch"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Breakfast with Keven").build()));

        // Mixed-case keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("kEvEn", "bReaKfAsT"));
        assertTrue(predicate.test(new ActivityBuilder().withTitle("Breakfast with Keven").build()));
    }

    @Test
    public void test_titleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TitleContainsKeywordsPredicate predicate = new TitleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ActivityBuilder().withTitle("Breakfast with Keven").build()));

        // Non-matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("John"));
        assertFalse(predicate.test(new ActivityBuilder().withTitle("Breakfast with Keven").build()));

        // Keywords match participant name, but does not match title
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(TypicalActivities.BREAKFAST));
    }
}
