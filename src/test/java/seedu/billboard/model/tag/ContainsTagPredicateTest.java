package seedu.billboard.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.billboard.testutil.ExpenseBuilder;

public class ContainsTagPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstPredicateTagNameList = new HashSet<>(Collections.singletonList(new Tag("first")));
        Set<Tag> secondPredicateTagNameList = new HashSet<>(Arrays.asList(new Tag("first"), new Tag("second")));

        ContainsTagPredicate firstPredicate = new ContainsTagPredicate(firstPredicateTagNameList);
        ContainsTagPredicate secondPredicate = new ContainsTagPredicate(secondPredicateTagNameList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsTagPredicate firstPredicateCopy = new ContainsTagPredicate(firstPredicateTagNameList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsTagName_returnsTrue() {
        // One tag name
        ContainsTagPredicate predicate = new ContainsTagPredicate(
                new HashSet<>(Collections.singletonList(new Tag("school"))));
        assertTrue(predicate.test(new ExpenseBuilder().withTags("school").build()));

        // Multiple tag names
        predicate = new ContainsTagPredicate(new HashSet<>(Arrays.asList(new Tag("school"), new Tag("cs"))));
        assertTrue(predicate.test(new ExpenseBuilder().withTags("school", "cs").build()));

        // Only one matching tag name
        predicate = new ContainsTagPredicate(new HashSet<>(Arrays.asList(new Tag("school"), new Tag("cs"))));
        assertTrue(predicate.test(new ExpenseBuilder().withTags("school").build()));
    }

    @Test
    public void test_tagDoesNotContainTagNames_returnsFalse() {
        //empty tag name
        ContainsTagPredicate predicate = new ContainsTagPredicate(new HashSet<>());
        assertFalse(predicate.test(new ExpenseBuilder().withTags("school").build()));

        // Non-matching tag name
        predicate = new ContainsTagPredicate(new HashSet<>(Collections.singletonList(new Tag("drinks"))));
        assertFalse(predicate.test(new ExpenseBuilder().withTags("food").build()));

        // Keywords match description and amount but does not match name
        predicate = new ContainsTagPredicate(new HashSet<>(Arrays.asList(new Tag("food"))));
        assertFalse(predicate.test(new ExpenseBuilder().withName("food")
                .withDescription("bought food").withAmount("9.60").withTags("school").build()));

        // Case sensitive
        predicate = new ContainsTagPredicate(new HashSet<>(Collections.singletonList(new Tag("drinks"))));
        assertFalse(predicate.test(new ExpenseBuilder().withTags("DRINKS").build()));
    }
}
