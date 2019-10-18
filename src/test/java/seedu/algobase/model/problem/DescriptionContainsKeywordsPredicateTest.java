package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class DescriptionContainsKeywordsPredicateTest {

    private static final String KEYWORD_NOT_IN_QUICK_SORT_DESCRIPTION = "1mP0ss1ble";

    @Test
    public void test_problemDescriptionContainsKeywords_returnTrue() {
        DescriptionContainsKeywordsPredicate predicate =
            new DescriptionContainsKeywordsPredicate(
                Arrays.asList(QUICK_SORT.getDescription().value.split(" ")));
        assertEquals(true, predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintKeywordNotSeparatedBySpaces_returnFalse() {
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(
                        Arrays.asList(QUICK_SORT.getDescription().value.replace(" ", "")));
        assertEquals(false, predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintContainsExtraKeywords_returnFalse() {
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(
                        Arrays.asList(QUICK_SORT.getDescription().value.split(" ")[0],
                            KEYWORD_NOT_IN_QUICK_SORT_DESCRIPTION));
        assertEquals(false, predicate.test(QUICK_SORT));
    }

}
