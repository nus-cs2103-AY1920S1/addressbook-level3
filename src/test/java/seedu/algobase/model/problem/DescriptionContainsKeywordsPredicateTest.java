//@@author le0tan
package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

class DescriptionContainsKeywordsPredicateTest {

    private static final String KEYWORD_NOT_IN_QUICK_SORT_DESCRIPTION = "1mP0ss1ble";

    @Test
    public void test_problemDescriptionContainsKeywords_returnTrue() {
        List<Keyword> list = new ArrayList<>();
        for (String s : QUICK_SORT.getDescription().value.split(" ")) {
            Keyword keyword = new Keyword(s);
            list.add(keyword);
        }
        DescriptionContainsKeywordsPredicate predicate =
            new DescriptionContainsKeywordsPredicate(
                list);
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintKeywordNotSeparatedBySpaces_returnFalse() {
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(
                    Collections.singletonList(new Keyword(QUICK_SORT.getDescription().value.replace(" ", ""))));
        assertFalse(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintContainsExtraKeywords_returnFalse() {
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(
                        Arrays.asList(new Keyword(QUICK_SORT.getDescription().value.split(" ")[0]),
                            new Keyword(KEYWORD_NOT_IN_QUICK_SORT_DESCRIPTION)));
        assertFalse(predicate.test(QUICK_SORT));
    }

}
