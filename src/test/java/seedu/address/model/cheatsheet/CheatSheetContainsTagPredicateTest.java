package seedu.address.model.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.CheatSheetBuilder;

public class CheatSheetContainsTagPredicateTest {
    @Test
    public void equals() {
        HashSet<Tag> firstPredicateTagList = new HashSet<>();
        firstPredicateTagList.add(new Tag("first"));

        HashSet<Tag> secondPredicateTagList = new HashSet<>();
        secondPredicateTagList.add(new Tag("first"));
        secondPredicateTagList.add(new Tag("second"));

        CheatSheetContainsTagPredicate firstPredicate =
                new CheatSheetContainsTagPredicate(firstPredicateTagList);
        CheatSheetContainsTagPredicate secondPredicate =
                new CheatSheetContainsTagPredicate(secondPredicateTagList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CheatSheetContainsTagPredicate firstPredicateCopy =
                new CheatSheetContainsTagPredicate(firstPredicateTagList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different cheatsheet -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_cheatSheetContainsTag_returnsTrue() {
        HashSet<Tag> firstPredicateTagList = new HashSet<>();
        firstPredicateTagList.add(new Tag("first"));

        HashSet<Tag> secondPredicateTagList = new HashSet<>();
        secondPredicateTagList.add(new Tag("first"));
        secondPredicateTagList.add(new Tag("second"));

        // One keyword
        CheatSheetContainsTagPredicate predicate =
                new CheatSheetContainsTagPredicate(firstPredicateTagList);
        assertTrue(predicate.test(new CheatSheetBuilder().withTags("first").build()));

        // Multiple keywords
        predicate = new CheatSheetContainsTagPredicate(secondPredicateTagList);
        assertTrue(predicate.test(new CheatSheetBuilder().withTags("first", "second").build()));

        // Only one matching keyword
        //predicate = new CheatSheetContainsTagPredicate(secondPredicateTagList);
        //assertTrue(predicate.test(new CheatSheetBuilder().withTags("first", "third").build()));

        // Mixed-case keywords
        //predicate = new CheatSheetContainsTagPredicate(secondPredicateTagList);
        //assertTrue(predicate.test(new CheatSheetBuilder().withTags("first", "seCoUnD").build()));
    }

    @Test
    public void test_cheatSheetDoesNotContainTags_returnsFalse() {
        HashSet<Tag> firstPredicateTagList = new HashSet<>();
        firstPredicateTagList.add(new Tag("first"));

        HashSet<Tag> secondPredicateTagList = new HashSet<>();
        secondPredicateTagList.add(new Tag("first"));
        secondPredicateTagList.add(new Tag("second"));

        // Zero keywords
        CheatSheetContainsTagPredicate predicate = new CheatSheetContainsTagPredicate(new HashSet<>());
        assertFalse(predicate.test(new CheatSheetBuilder().withTags("first").build()));

        // Non-matching keyword
        predicate = new CheatSheetContainsTagPredicate(firstPredicateTagList);
        assertFalse(predicate.test(new CheatSheetBuilder().withTags("third").build()));

        // Matches title, but does not match tag
        predicate = new CheatSheetContainsTagPredicate(firstPredicateTagList);
        assertFalse(predicate.test(new CheatSheetBuilder().withTitle("first").withTags("third").build()));

        // Only one matching keyword
        predicate = new CheatSheetContainsTagPredicate(secondPredicateTagList);
        assertFalse(predicate.test(new CheatSheetBuilder().withTags("first", "third").build()));
    }
}
