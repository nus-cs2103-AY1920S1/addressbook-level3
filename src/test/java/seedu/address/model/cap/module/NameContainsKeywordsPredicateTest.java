package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModuleBuilder;

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
        NameContainsKeywordsPredicate firstPredicateCopy =
                new NameContainsKeywordsPredicate(firstPredicateKeywordList);
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
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("CS2103"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103 CS2100").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2103", "CS2100"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103 CS2100").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2100", "CS2100"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103 CS2100").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cS2103", "cs2100"));
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103 CS2100").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2101"));
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2103 CS2100").build()));
    }
}
