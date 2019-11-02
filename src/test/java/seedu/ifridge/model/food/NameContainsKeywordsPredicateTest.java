package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ifridge.testutil.GroceryItemBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
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
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Banana"));
        assertTrue(predicate.test(new GroceryItemBuilder().withName("Ripe Banana").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Ripe", "Banana"));
        assertTrue(predicate.test(new GroceryItemBuilder().withName("Ripe Banana").build()));

        // Only one keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Ripe", "Banana"));
        assertTrue(predicate.test(new GroceryItemBuilder().withName("Rotten Banana").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("RiPe", "bANAna"));
        assertTrue(predicate.test(new GroceryItemBuilder().withName("Ripe Banana").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GroceryItemBuilder().withName("Banana").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Rambutan"));
        assertFalse(predicate.test(new GroceryItemBuilder().withName("Keitt mango").build()));

        // Keywords match amount, expiry date, but not name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Kiwi", "500ml", "10/08/2020"));
        assertFalse(predicate.test(new GroceryItemBuilder().withName("Pizza").build()));
    }
}
