package seedu.mark.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.parser.AutotagCommandParser.BookmarkPredicateBuilder;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.predicates.NameContainsKeywordsPredicate;

public class BookmarkPredicateBuilderTest {

    private static final Predicate<Bookmark> CONDITION_1 = new NameContainsKeywordsPredicate(Arrays.asList("hello"));
    private static final Predicate<Bookmark> CONDITION_2 = new NameContainsKeywordsPredicate(Arrays.asList("world"));

    @Test
    public void equals() {
        BookmarkPredicateBuilder firstPredicateBuilder = new BookmarkPredicateBuilder().addCondition(CONDITION_1);
        BookmarkPredicateBuilder secondPredicateBuilder = new BookmarkPredicateBuilder().addNotCondition(CONDITION_2);

        // same values -> returns true
        BookmarkPredicateBuilder builderWithSameValues = new BookmarkPredicateBuilder().addCondition(CONDITION_1);
        assertTrue(firstPredicateBuilder.equals(builderWithSameValues));

        // same object -> returns true
        assertTrue(firstPredicateBuilder.equals(firstPredicateBuilder));

        // null -> returns false
        assertFalse(firstPredicateBuilder.equals(null));

        // different types -> returns false
        assertFalse(firstPredicateBuilder.equals(5));

        // different fields -> returns false
        assertFalse(firstPredicateBuilder.equals(secondPredicateBuilder));

        // different condition -> returns false
        BookmarkPredicateBuilder otherPredicateBuilder = new BookmarkPredicateBuilder().addCondition(CONDITION_2);
        assertFalse(firstPredicateBuilder.equals(otherPredicateBuilder));

        // different not-condition -> returns false
        otherPredicateBuilder = new BookmarkPredicateBuilder().addNotCondition(CONDITION_1);
        assertFalse(firstPredicateBuilder.equals(otherPredicateBuilder));
    }
}
