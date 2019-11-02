package dukecooks.model.dashboard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.model.dashboard.components.DashboardNameContainsKeywordsPredicate;
import dukecooks.testutil.dashboard.DashboardBuilder;

public class DashboardNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DashboardNameContainsKeywordsPredicate firstPredicate = new DashboardNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        DashboardNameContainsKeywordsPredicate secondPredicate = new DashboardNameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DashboardNameContainsKeywordsPredicate firstPredicateCopy = new DashboardNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different recipe -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        DashboardNameContainsKeywordsPredicate predicate = new DashboardNameContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new DashboardBuilder().withDashboardName("Alice Bob").build()));

        // Multiple keywords
        predicate = new DashboardNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new DashboardBuilder().withDashboardName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new DashboardNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new DashboardBuilder().withDashboardName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new DashboardNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new DashboardBuilder().withDashboardName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DashboardNameContainsKeywordsPredicate predicate = new DashboardNameContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new DashboardBuilder().withDashboardName("Alice").build()));

        // Non-matching keyword
        predicate = new DashboardNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new DashboardBuilder().withDashboardName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new DashboardNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new DashboardBuilder().withDashboardName("Alice").build()));
    }
}
