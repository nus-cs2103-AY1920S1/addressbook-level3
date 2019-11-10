package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.IntervieweeBuilder;

public class IntervieweeNameHasKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        IntervieweeNameHasKeywordsPredicate firstPredicate =
                new IntervieweeNameHasKeywordsPredicate(firstPredicateKeywordList);
        IntervieweeNameHasKeywordsPredicate secondPredicate =
                new IntervieweeNameHasKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IntervieweeNameHasKeywordsPredicate firstPredicateCopy =
                new IntervieweeNameHasKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different interviewee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        IntervieweeNameHasKeywordsPredicate predicate =
                new IntervieweeNameHasKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new IntervieweeBuilder(ALICE_INTERVIEWEE).build()));

        // Multiple keywords
        predicate = new IntervieweeNameHasKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new IntervieweeBuilder(ALICE_INTERVIEWEE).withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new IntervieweeNameHasKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new IntervieweeBuilder(ALICE_INTERVIEWEE).withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new IntervieweeNameHasKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new IntervieweeBuilder(ALICE_INTERVIEWEE).withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        IntervieweeNameHasKeywordsPredicate predicate =
                new IntervieweeNameHasKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new IntervieweeBuilder(ALICE_INTERVIEWEE).withName("Alice").build()));

        // Non-matching keyword
        predicate = new IntervieweeNameHasKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new IntervieweeBuilder(ALICE_INTERVIEWEE).withName("Alice Bob").build()));

        // Keywords match phone, but does not match name
        predicate = new IntervieweeNameHasKeywordsPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new IntervieweeBuilder(ALICE_INTERVIEWEE).withName("Alice")
                .withPhone("12345").build()));
    }

}
