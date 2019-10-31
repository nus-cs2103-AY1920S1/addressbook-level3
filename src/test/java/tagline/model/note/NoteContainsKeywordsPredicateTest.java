package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tagline.testutil.note.NoteBuilder;

public class NoteContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NoteContainsKeywordsPredicate firstPredicate =
                new NoteContainsKeywordsPredicate(firstPredicateKeywordList);
        NoteContainsKeywordsPredicate secondPredicate =
                new NoteContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NoteContainsKeywordsPredicate firstPredicateCopy =
                new NoteContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different note -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_noteContainsKeywords_returnsTrue() {
        // One keyword
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("Lorem"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("No match").withContent("Lorem ipsum.").build()));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Lorem ipsum").withContent("No match").build()));

        // Multiple keywords
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("Lorem", "ipsum"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("No match").withContent("Lorem ipsum.").build()));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Lorem ipsum").withContent("No match").build()));

        // Only one matching keyword
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("Lorem", "Praesent"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("No match").withContent("Lorem ipsum.").build()));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Lorem ipsum").withContent("No match").build()));

        // Mixed-case keywords
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("lOreM", "iPsuM"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("No match").withContent("Lorem ipsum.").build()));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Lorem ipsum").withContent("No match").build()));
    }

    @Test
    public void test_noteDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NoteContainsKeywordsPredicate predicate = new NoteContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new NoteBuilder().withTitle("No match").withContent("Lorem ipsum.").build()));
        assertFalse(predicate.test(new NoteBuilder().withTitle("Lorem ipsum").withContent("No match").build()));

        // Non-matching keyword
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("Praesent"));
        assertFalse(predicate.test(new NoteBuilder().withTitle("No match").withContent("Lorem ipsum.").build()));
        assertFalse(predicate.test(new NoteBuilder().withTitle("Lorem ipsum").withContent("No match").build()));
    }
}
