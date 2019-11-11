package seedu.address.transaction.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;

class TransactionContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TransactionContainsKeywordsPredicate firstPredicate =
                new TransactionContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionContainsKeywordsPredicate secondPredicate =
                new TransactionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TransactionContainsKeywordsPredicate firstPredicateCopy =
                new TransactionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_transactionContainsKeywords_returnsTrue() {
        // One keyword
        TransactionContainsKeywordsPredicate predicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new TransactionBuilder(TypicalPersons.ALICE).build()));

        // Multiple keywords
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("Alice", "Pauline"));
        assertTrue(predicate.test(new TransactionBuilder(TypicalPersons.ALICE).build()));

        // Only one matching keyword
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new TransactionBuilder(new PersonBuilder().withName("Alice Carol").build()).build()));

        // Mixed-case keywords
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("aLIce", "PauLiNe"));
        assertTrue(predicate.test(new TransactionBuilder(TypicalPersons.ALICE).build()));
    }

    @Test
    public void test_transactionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionContainsKeywordsPredicate predicate = new
                TransactionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder(TypicalPersons.ALICE).build()));

        // Non-matching keyword
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new TransactionBuilder(TypicalPersons.ALICE).build()));
    }

}
