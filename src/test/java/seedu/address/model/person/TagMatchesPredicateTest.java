package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagMatchesPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        TagMatchesPredicate firstPredicate = new TagMatchesPredicate(firstPredicateKeyword);
        TagMatchesPredicate secondPredicate = new TagMatchesPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchesPredicate firstPredicateCopy = new TagMatchesPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagMatchesQuery_returnsTrue() {
        // One keyword
        TagMatchesPredicate predicate = new TagMatchesPredicate("friend");
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Mixed-case keywords
        predicate = new TagMatchesPredicate("FRIEND");
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));
    }

    @Test
    public void test_tagDoesNotMatchQuery_returnsFalse() {
        // Zero keywords
        TagMatchesPredicate predicate = new TagMatchesPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Non-matching keyword
        predicate = new TagMatchesPredicate("colleague");
        assertFalse(predicate.test(new PersonBuilder().withTags("college").build()));

    }
}
