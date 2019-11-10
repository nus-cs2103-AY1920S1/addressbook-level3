//@@author le0tan
package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;
import seedu.algobase.model.tag.Tag;

class TagIncludesKeywordsPredicateTest {

    private static final String KEYWORD_NOT_IN_QUICK_SORT_TAGS = "exttttremely_impossible";

    @Test
    public void test_tagIncludesKeywords_returnTrue() {
        Set<Tag> tags = QUICK_SORT.getTags();
        List<String> tagList = new ArrayList<>();
        for (Tag tag: tags) {
            tagList.add(tag.tagName);
        }
        TagIncludesKeywordsPredicate predicate =
            new TagIncludesKeywordsPredicate(tagList.stream().map(Keyword::new).collect(Collectors.toList()));
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_tagDoesNotIncludeKeywords_returnFalse() {
        Keyword keyword = new Keyword(KEYWORD_NOT_IN_QUICK_SORT_TAGS);
        TagIncludesKeywordsPredicate predicate =
                new TagIncludesKeywordsPredicate(Collections.singletonList(keyword));
        assertFalse(predicate.test(QUICK_SORT));
    }

}
