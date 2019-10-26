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
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagMatchesPredicate firstPredicate = new TagMatchesPredicate(firstPredicateKeywordList);
        TagMatchesPredicate secondPredicate = new TagMatchesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchesPredicate firstPredicateCopy = new TagMatchesPredicate(firstPredicateKeywordList);
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
        // One tag
        TagMatchesPredicate predicate = new TagMatchesPredicate(Collections.singletonList("captain"));
        assertTrue(predicate.test(new PersonBuilder().withTags("captain").build()));

        // Multiple tag
        predicate = new TagMatchesPredicate(Arrays.asList("captain", "freestyle"));
        assertTrue(predicate.test(new PersonBuilder().withTags("captain", "freestyle").build()));

        // Only one matching tag
        predicate = new TagMatchesPredicate(Arrays.asList("captain", "freestyle"));
        assertTrue(predicate.test(new PersonBuilder().withTags("captain", "butterfly").build()));

        // Mixed-case tags
        predicate = new TagMatchesPredicate(Arrays.asList("cApTaIn", "FrEeStYlE"));
        assertTrue(predicate.test(new PersonBuilder().withTags("captain", "freestyle").build()));
    }

    @Test
    public void test_tagDoesNotMatchQuery_returnsFalse() {
        // Zero tags
        TagMatchesPredicate predicate = new TagMatchesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("captain").build()));

        // Non-matching tag
        predicate = new TagMatchesPredicate(Arrays.asList("captain"));
        assertFalse(predicate.test(new PersonBuilder().withTags("butterfly", "injured").build()));
    }
}
