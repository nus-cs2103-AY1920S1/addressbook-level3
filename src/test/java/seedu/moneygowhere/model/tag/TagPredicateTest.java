package seedu.moneygowhere.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.testutil.SpendingBuilder;

//@author Nanosync
public class TagPredicateTest {

    @Test
    public void equals() {
        Set<String> tagKeywordSet = Set.of("tag");
        TagPredicate firstPredicate = new TagPredicate(tagKeywordSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagPredicate firstPredicateCopy = new TagPredicate(tagKeywordSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);
    }

    @Test
    public void tagPredicate_matchingTags_returnsTrue() {
        // Only one tag matches
        TagPredicate predicate2 = new TagPredicate(Set.of("three"));
        assertTrue(predicate2.test(new SpendingBuilder().withTags("one", "two", "three").build()));

        // Both tags match
        TagPredicate predicate = new TagPredicate(Set.of("one", "two"));
        assertTrue(predicate.test(new SpendingBuilder().withTags("one", "two").build()));
    }

    @Test
    public void tagPredicate_missingTags_returnsFalse() {
        // Tag does not exist
        TagPredicate predicate = new TagPredicate(Set.of("one"));
        assertFalse(predicate.test(new SpendingBuilder().withTags("two").build()));
        assertFalse(predicate.test(new SpendingBuilder().withTags("three", "two").build()));
    }
}
