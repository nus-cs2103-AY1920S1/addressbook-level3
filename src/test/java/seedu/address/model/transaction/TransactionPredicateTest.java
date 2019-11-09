package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.category.Category;
import seedu.address.testutil.BankOperationBuilder;


public class TransactionPredicateTest {

    @Test
    public void equals() {
        Optional<Set<Category>> firstPredicateKeywordList = Optional.of(
            new HashSet<>(Collections.singletonList(new Category("first"))));
        Optional<Set<Category>> secondPredicateKeywordList = Optional.of(new HashSet<>(Arrays.asList(
            new Category("first"), new Category("second"))));

        TransactionPredicate firstPredicate =
            new TransactionPredicate(firstPredicateKeywordList,
                Optional.empty(), Optional.empty(), Optional.empty());
        TransactionPredicate secondPredicate =
            new TransactionPredicate(secondPredicateKeywordList,
                Optional.empty(), Optional.empty(), Optional.empty());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TransactionPredicate firstPredicateCopy =
            new TransactionPredicate(firstPredicateKeywordList,
                Optional.empty(), Optional.empty(), Optional.empty());
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
        Optional<Set<Category>> oneKeyword = Optional.of(
            new HashSet<>(Collections.singletonList(new Category("Alice"))));
        TransactionPredicate predicate = new TransactionPredicate(oneKeyword,
            Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));

        // Multiple keywords
        Optional<Set<Category>> multipleKeyword = Optional.of(
            new HashSet<>(Arrays.asList(new Category("Alice"), new Category("Bob"))));
        predicate = new TransactionPredicate(multipleKeyword,
            Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));

        // Only one matching keyword
        Optional<Set<Category>> oneMatch = Optional.of(
            new HashSet<>(Arrays.asList(new Category("Bob"), new Category("Carol"))));
        predicate = new TransactionPredicate(oneMatch,
            Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Carol").build()));

        // Mixed-case keywords
        Optional<Set<Category>> mixedCase = Optional.of(
            new HashSet<>(Arrays.asList(new Category("aLIce"), new Category("bOB"))));
        predicate = new TransactionPredicate(mixedCase,
            Optional.empty(), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionPredicate predicate =
            new TransactionPredicate(Optional.of(new HashSet<>()),
                Optional.empty(), Optional.empty(), Optional.empty());
        assertFalse(predicate.test(new BankOperationBuilder().withCategories("Alice").build()));

        // Non-matching keyword
        Optional<Set<Category>> noneMatch = Optional.of(
            new HashSet<>(Arrays.asList(new Category("Carol"))));
        predicate = new TransactionPredicate(noneMatch,
            Optional.empty(), Optional.empty(), Optional.empty());
        assertFalse(predicate.test(new BankOperationBuilder().withCategories("Alice", "Bob").build()));

        // Keywords match amount and date, but does not match category
        Optional<Set<Category>> wrongMatch = Optional.of(
            new HashSet<>(Arrays.asList(new Category("12345"), new Category("19112019"))));
        predicate = new TransactionPredicate(wrongMatch,
            Optional.empty(), Optional.empty(), Optional.empty());
        assertFalse(predicate.test(new BankOperationBuilder().withCategories("Alice").withAmount("12345")
            .withDate("19112019").build()));
    }

    @Test
    public void test_noCategory_returnsFalse() {
        TransactionPredicate predicate =
            new TransactionPredicate(Optional.empty(),
                Optional.empty(), Optional.of(1), Optional.empty());
        assertFalse(predicate.test(new BankOperationBuilder().withCategories("Alice").build()));
    }

    @Test
    public void test_null_throwsNullPointerException() {
        TransactionPredicate predicate =
            new TransactionPredicate(Optional.empty(),
                Optional.empty(), Optional.of(1), Optional.empty());
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

}
