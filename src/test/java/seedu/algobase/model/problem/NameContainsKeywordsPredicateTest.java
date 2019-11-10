//@@author le0tan
package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;

class NameContainsKeywordsPredicateTest {

    private static final String KEYWORD_NOT_IN_QUICK_SORT_NAME = "1mP0ss1ble";

    @Test
    public void test_problemNameContainsKeywords_returnTrue() {
        List<Keyword> list = new ArrayList<>();
        for (String s : QUICK_SORT.getName().fullName.split(" ")) {
            Keyword keyword = new Keyword(s);
            list.add(keyword);
        }
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(
                    list);
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintKeywordNotSeparatedBySpaces_returnFalse() {
        Keyword keyword = new Keyword(QUICK_SORT.getName().fullName.replace(" ", ""));
        NameContainsKeywordsPredicate predicate =
            new NameContainsKeywordsPredicate(Collections.singletonList(keyword));
        assertFalse(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_constraintContainsExtraKeywords_returnTrue() {
        NameContainsKeywordsPredicate predicate =
            new NameContainsKeywordsPredicate(
                    Arrays.asList(new Keyword(QUICK_SORT.getName().fullName.split(" ")[0]),
                        new Keyword(KEYWORD_NOT_IN_QUICK_SORT_NAME)));
        assertEquals(true, predicate.test(QUICK_SORT));
    }

}
