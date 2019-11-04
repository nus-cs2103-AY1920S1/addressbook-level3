package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ifridge.testutil.GroceryItemBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("dinner"));
        assertTrue(predicate.test(new GroceryItemBuilder().withTags("dinner", "dish").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("dinner", "dish"));
        assertTrue(predicate.test(new GroceryItemBuilder().withTags("dinner", "dish", "mexican").build()));

        // Only one keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("dinner", "dish"));
        assertTrue(predicate.test(new GroceryItemBuilder().withTags("dinner", "mexican").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("DinNer", "dISh"));
        assertTrue(predicate.test(new GroceryItemBuilder().withTags("dinner", "dish").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GroceryItemBuilder().withTags("dinner").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("dinner"));
        assertFalse(predicate.test(new GroceryItemBuilder().withTags("lunch", "takeaway").build()));

        // Keywords match amount, expiry date, but not tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Kiwi", "500ml", "10/08/2020"));
        assertFalse(predicate.test(new GroceryItemBuilder().withTags("snack").build()));
    }
}
