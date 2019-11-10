package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.InterviewerBuilder;

public class InterviewerNameHasKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        InterviewerNameHasKeywordsPredicate firstPredicate =
                new InterviewerNameHasKeywordsPredicate(firstPredicateKeywordList);
        InterviewerNameHasKeywordsPredicate secondPredicate =
                new InterviewerNameHasKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InterviewerNameHasKeywordsPredicate firstPredicateCopy =
                new InterviewerNameHasKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different interviewer -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        InterviewerNameHasKeywordsPredicate predicate =
                new InterviewerNameHasKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new InterviewerBuilder(ALICE_INTERVIEWER).build()));

        // Multiple keywords
        predicate = new InterviewerNameHasKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new InterviewerBuilder(ALICE_INTERVIEWER).withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new InterviewerNameHasKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new InterviewerBuilder(ALICE_INTERVIEWER).withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new InterviewerNameHasKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new InterviewerBuilder(ALICE_INTERVIEWER).withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        InterviewerNameHasKeywordsPredicate predicate =
                new InterviewerNameHasKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new InterviewerBuilder(ALICE_INTERVIEWER).withName("Alice").build()));

        // Non-matching keyword
        predicate = new InterviewerNameHasKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new InterviewerBuilder(ALICE_INTERVIEWER).withName("Alice Bob").build()));

        // Keywords match phone, but does not match name
        predicate = new InterviewerNameHasKeywordsPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new InterviewerBuilder(ALICE_INTERVIEWER).withName("Alice")
                .withPhone("12345").build()));
    }

}
