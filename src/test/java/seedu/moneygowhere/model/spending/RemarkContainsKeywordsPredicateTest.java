package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.testutil.SpendingBuilder;

//@author Nanosync
public class RemarkContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RemarkContainsKeywordsPredicate firstPredicate =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        RemarkContainsKeywordsPredicate secondPredicate =
                new RemarkContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RemarkContainsKeywordsPredicate firstPredicateCopy =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different predicate list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void remarkPredicate_remarkContainsKeywords_returnsTrue() {
        // One keyword
        RemarkContainsKeywordsPredicate predicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList("watch"));
        assertTrue(predicate.test(new SpendingBuilder().withRemark("Likes to watch movies").build()));

        // Multiple keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("watch", "movies"));
        assertTrue(predicate.test(new SpendingBuilder().withRemark("Likes to watch movies").build()));

        // Only one matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Likes", "swimming"));
        assertTrue(predicate.test(new SpendingBuilder().withRemark("Likes movies").build()));

        // Mixed-case keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("taSty", "fOod"));
        assertTrue(predicate.test(new SpendingBuilder().withRemark("tasty food").build()));
    }

    @Test
    public void remarkPredicate_remarkDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new SpendingBuilder().withRemark("Apple").build()));

        // Non-matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Collections.singletonList("Chicken"));
        assertFalse(predicate.test(new SpendingBuilder().withRemark("Apple Briyani").build()));

        // Keywords match name, date, but does not match remark
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("1/1/2019", "Apple"));
        assertFalse(predicate.test(new SpendingBuilder().withName("Apple").withDate("1/1/2019")
                .withRemark("Tasty").build()));
    }
}
