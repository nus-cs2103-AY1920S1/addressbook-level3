package seedu.moneygowhere.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.testutil.SpendingBuilder;

public class TagPredicateTest {

    @Test
    public void equals() {
        TagPredicate firstPredicate = new TagPredicate("tag");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagPredicate firstPredicateCopy = new TagPredicate("tag");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_tagPredicate_returnsTrue() {
        // Tag exists
        TagPredicate predicate = new TagPredicate("one");
        assertTrue(predicate.test(new SpendingBuilder().withTags("one").build()));
        assertTrue(predicate.test(new SpendingBuilder().withTags("one", "two").build()));
    }

    @Test
    public void test_tagPredicate_returnsFalse() {
        // Tag does not exist
        TagPredicate predicate = new TagPredicate("one");
        assertFalse(predicate.test(new SpendingBuilder().withTags("two").build()));
        assertFalse(predicate.test(new SpendingBuilder().withTags("three", "two").build()));
    }
}
