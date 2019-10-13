package io.xpire.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.testutil.ItemBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsKeywordsPredicate firstPredicate = new ContainsKeywordsPredicate(firstPredicateKeywordList);
        ContainsKeywordsPredicate secondPredicate = new ContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
<<<<<<< HEAD:src/test/java/io/xpire/model/item/ContainsKeywordsPredicateTest.java
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ItemBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ItemBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ItemBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ItemBuilder().withName("Alice Bob").build()));
=======
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Apple"));
        assertTrue(predicate.test(new ItemBuilder().withName("Apple Banana").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Apple", "Banana"));
        assertTrue(predicate.test(new ItemBuilder().withName("Apple Banana").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Banana", "Carrot"));
        assertTrue(predicate.test(new ItemBuilder().withName("Apple Carrot").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aPPle", "bANana"));
        assertTrue(predicate.test(new ItemBuilder().withName("Apple Banana").build()));
>>>>>>> master:src/test/java/io/xpire/model/item/NameContainsKeywordsPredicateTest.java
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
<<<<<<< HEAD:src/test/java/io/xpire/model/item/ContainsKeywordsPredicateTest.java
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ItemBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ItemBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ItemBuilder().withName("Alice").withExpiryDate("01/02/2019").build()));
=======
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ItemBuilder().withName("Apple").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carrot"));
        assertFalse(predicate.test(new ItemBuilder().withName("Apple Banana").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ItemBuilder().withName("Apple").withExpiryDate("01/02/2020").build()));
>>>>>>> master:src/test/java/io/xpire/model/item/NameContainsKeywordsPredicateTest.java
    }
}
