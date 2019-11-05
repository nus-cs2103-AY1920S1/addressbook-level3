package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BankOperationBuilder;


public class TransactionContainsCategoriesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TransactionContainsCategoriesPredicate firstPredicate =
            new TransactionContainsCategoriesPredicate(firstPredicateKeywordList);
        TransactionContainsCategoriesPredicate secondPredicate =
            new TransactionContainsCategoriesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TransactionContainsCategoriesPredicate firstPredicateCopy =
            new TransactionContainsCategoriesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_categoryContainsKeywords_returnsTrue() {
        // One keyword
        TransactionContainsCategoriesPredicate predicate =
            new TransactionContainsCategoriesPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));

        // Multiple keywords
        predicate = new TransactionContainsCategoriesPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));

        // Only one matching keyword
        predicate = new TransactionContainsCategoriesPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Carol").build()));

        // Mixed-case keywords
        predicate = new TransactionContainsCategoriesPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionContainsCategoriesPredicate predicate =
            new TransactionContainsCategoriesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BankOperationBuilder().withCategories("Alice").build()));

        // Non-matching keyword
        predicate = new TransactionContainsCategoriesPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));

        // Keywords match amount and date, but does not match category
        predicate = new TransactionContainsCategoriesPredicate(Arrays.asList("12345", "19112019"));
        assertFalse(predicate.test(new BankOperationBuilder().withCategories("Alice").withAmount("12345")
            .withDate("19112019").build()));
    }

}
