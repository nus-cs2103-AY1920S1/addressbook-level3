package seedu.address.model.earnings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EarningsBuilder;

public class ClassIdContainKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ClassIdContainKeywordPredicate firstPredicate = new ClassIdContainKeywordPredicate(firstPredicateKeywordList);
        ClassIdContainKeywordPredicate secondPredicate = new ClassIdContainKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClassIdContainKeywordPredicate firstPredicateCopy =
                new ClassIdContainKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_classIdContainsKeywords_returnsTrue() {
        // One keyword
        ClassIdContainKeywordPredicate predicate = new ClassIdContainKeywordPredicate(Collections.singletonList("CS"));
        assertTrue(predicate.test(new EarningsBuilder().withClassId("CS2103T").build()));

        // Multiple keywords
        predicate = new ClassIdContainKeywordPredicate(Arrays.asList("CS", "MA"));
        assertTrue(predicate.test(new EarningsBuilder().withClassId("CSMA1000").build()));

        // Only one matching keyword
        predicate = new ClassIdContainKeywordPredicate(Arrays.asList("CS2100", "MA1000"));
        assertTrue(predicate.test(new EarningsBuilder().withClassId("MA1000").build()));

        // Mixed-case keywords
        predicate = new ClassIdContainKeywordPredicate(Arrays.asList("cS", "Ma"));
        assertTrue(predicate.test(new EarningsBuilder().withClassId("CSMA2100S").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ClassIdContainKeywordPredicate predicate = new ClassIdContainKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EarningsBuilder().withClassId("CS2100").build()));

        // Non-matching keyword
        predicate = new ClassIdContainKeywordPredicate(Arrays.asList("CS2103T"));
        assertFalse(predicate.test(new EarningsBuilder().withClassId("MA1000").build()));

    }
}
