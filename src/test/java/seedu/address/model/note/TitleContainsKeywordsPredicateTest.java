package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.NoteBuilder;

class NoteContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NoteContainsKeywordsPredicate firstPredicate = new NoteContainsKeywordsPredicate(firstPredicateKeywordList);
        NoteContainsKeywordsPredicate secondPredicate = new NoteContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NoteContainsKeywordsPredicate firstPredicateCopy = new NoteContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_noteContainsKeywords_returnsTrue() {
        // One keyword
        NoteContainsKeywordsPredicate predicate = new NoteContainsKeywordsPredicate(Collections.singletonList("Diary"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Diary one").build()));
        assertTrue(predicate.test(new NoteBuilder().withDescription("Diary one").build()));
        assertTrue(predicate.test(new NoteBuilder().withContent("Diary one").build()));

        // Multiple keywords
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("Diary", "One"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Diary One").build()));
        assertTrue(predicate.test(new NoteBuilder().withDescription("Diary one").build()));
        assertTrue(predicate.test(new NoteBuilder().withContent("Diary one").build()));

        // Only one matching keyword
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("Project", "Test"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Project Test").build()));
        assertTrue(predicate.test(new NoteBuilder().withDescription("Project Test").build()));
        assertTrue(predicate.test(new NoteBuilder().withContent("Project Test").build()));

        // Mixed-case keywords
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("dIary", "oNE"));
        assertTrue(predicate.test(new NoteBuilder().withTitle("Diary One").build()));
        assertTrue(predicate.test(new NoteBuilder().withDescription("Diary One").build()));
        assertTrue(predicate.test(new NoteBuilder().withContent("Diary One").build()));


    }

    @Test
    public void test_noteDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NoteContainsKeywordsPredicate predicate = new NoteContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new NoteBuilder().withTitle("Diary").build()));

        // Non-matching keyword
        predicate = new NoteContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new NoteBuilder().withTitle("Alice Bob").build()));
        assertFalse(predicate.test(new NoteBuilder().withContent("Alice Bob").build()));
        assertFalse(predicate.test(new NoteBuilder().withDescription("Alice Bob").build()));

    }
}
