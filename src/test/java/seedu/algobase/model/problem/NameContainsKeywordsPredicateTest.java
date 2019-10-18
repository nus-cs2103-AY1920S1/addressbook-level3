package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class NameContainsKeywordsPredicateTest {

    private static final String KEYWORD_NOT_IN_QUICK_SORT_NAME = "1mP0ss1ble";

    @Test
    public void test_problemNameContainsKeywords_returnTrue() {
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(
                        Arrays.asList(QUICK_SORT.getName().fullName.split(" ")));
        assertEquals(true, predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintKeywordNotSeparatedBySpaces_returnFalse() {
        NameContainsKeywordsPredicate predicate =
            new NameContainsKeywordsPredicate(
                    Arrays.asList(QUICK_SORT.getName().fullName.replace(" ", "")));
        assertEquals(false, predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintContainsExtraKeywords_returnTrue() {
        NameContainsKeywordsPredicate predicate =
            new NameContainsKeywordsPredicate(
                    Arrays.asList(QUICK_SORT.getName().fullName.split(" ")[0],
                            KEYWORD_NOT_IN_QUICK_SORT_NAME));
        assertEquals(true, predicate.test(QUICK_SORT));
    }

}
