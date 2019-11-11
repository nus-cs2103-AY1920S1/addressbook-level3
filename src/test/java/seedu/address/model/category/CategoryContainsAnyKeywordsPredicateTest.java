//@@author shutingy-reused
//original code is from NameContainsKeywordsPredicateTest.java by si jie
//reused with minor changes

package seedu.address.model.category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FlashCardBuilder;

public class CategoryContainsAnyKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordsList = Collections.singletonList("first");
        List<String> secondPredicateKeywordsList = Arrays.asList("first", "second");

        CategoryContainsAnyKeywordsPredicate firstPredicate =
                new CategoryContainsAnyKeywordsPredicate(firstPredicateKeywordsList);
        CategoryContainsAnyKeywordsPredicate secondPredicate =
                new CategoryContainsAnyKeywordsPredicate(secondPredicateKeywordsList);

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        //same values -> returns true
        CategoryContainsAnyKeywordsPredicate firstPredicateCopy =
                new CategoryContainsAnyKeywordsPredicate(firstPredicateKeywordsList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> return false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_categoryContainsAnyKeywords_returnsTrue() {
        //One keyword match
        CategoryContainsAnyKeywordsPredicate predicate =
                new CategoryContainsAnyKeywordsPredicate(Collections.singletonList("testing"));
        assertTrue(predicate.test(new FlashCardBuilder().withCatgeories("testing").build()));

        // Multiple keywords
        predicate = new CategoryContainsAnyKeywordsPredicate(Arrays.asList("testing", "category"));
        assertTrue(predicate.test(new FlashCardBuilder().withCatgeories("category", "testing").build()));

        // only one matching keyword
        predicate = new CategoryContainsAnyKeywordsPredicate(Arrays.asList("123", "cs"));
        assertTrue(predicate.test(new FlashCardBuilder().withCatgeories("cs", "testing").build()));

        // Mixed-case keywords
        predicate = new CategoryContainsAnyKeywordsPredicate(Arrays.asList("cS1232", "laLa"));
        assertTrue(predicate.test(new FlashCardBuilder().withCatgeories("CS1232", "LALA").build()));
    }

    @Test
    public void test_categoryDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CategoryContainsAnyKeywordsPredicate predicate =
                new CategoryContainsAnyKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new FlashCardBuilder().withCatgeories("cs").build()));

        // Non-matching keyword
        predicate = new CategoryContainsAnyKeywordsPredicate(Arrays.asList("addition"));
        assertFalse(predicate.test(new FlashCardBuilder().withCatgeories("quotient").build()));
    }

}
