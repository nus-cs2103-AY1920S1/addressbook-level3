package seedu.billboard.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.testutil.ExpenseBuilder;

public class AllContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AllContainsKeywordsPredicate firstPredicate = new AllContainsKeywordsPredicate(firstPredicateKeywordList);
        AllContainsKeywordsPredicate secondPredicate = new AllContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AllContainsKeywordsPredicate firstPredicateCopy = new AllContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        AllContainsKeywordsPredicate predicate = new AllContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Alice Bob").build()));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Bob").withDescription("Alice").build()));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Alice Bob").build()));

        // Multiple keywords
        predicate = new AllContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Bob").withDescription("Alice").build()));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Alice Bob").build()));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Alice Bob").build()));

        // Only one matching keyword
        predicate = new AllContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Carol").withDescription("Alice").build()));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Carol Alice").build()));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Carol Alice").build()));

        // Mixed-case keywords
        predicate = new AllContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Bob").withDescription("alice").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AllContainsKeywordsPredicate predicate = new AllContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ExpenseBuilder().withName("Alice").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("Alice").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withName("Alice").withDescription("Bob").build()));

        // Non-matching keyword
        predicate = new AllContainsKeywordsPredicate(Collections.singletonList("Carol"));
        assertFalse(predicate.test(new ExpenseBuilder().withName("Alice Bob").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("Alice Bob").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withName("Alice Bob").withDescription("David").build()));

        // Keywords match amount but does not match name and description
        predicate = new AllContainsKeywordsPredicate(Arrays.asList("9.60"));
        assertFalse(predicate.test(new ExpenseBuilder().withName("bills").withAmount("9.60").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("bills").withAmount("9.60").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withName("bills").withDescription("bills").withAmount("9.60")
                .build()));
    }
}
