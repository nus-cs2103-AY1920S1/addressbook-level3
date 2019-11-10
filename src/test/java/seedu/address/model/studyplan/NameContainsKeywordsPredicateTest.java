package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudyPlanBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different studyPlan -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("planA"));
        assertTrue(predicate.test(new StudyPlanBuilder().withTitle(new Title("planA")).build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("planA", "planB"));
        assertTrue(predicate.test(new StudyPlanBuilder().withTitle(new Title("planA")).build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("pLaNa", "PlaNB"));
        assertTrue(predicate.test(new StudyPlanBuilder().withTitle(new Title("planA")).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudyPlanBuilder().withTitle(new Title("planA")).build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("notaplan"));
        assertFalse(predicate.test(new StudyPlanBuilder().withTitle(new Title("planA")).build()));

    }

}
