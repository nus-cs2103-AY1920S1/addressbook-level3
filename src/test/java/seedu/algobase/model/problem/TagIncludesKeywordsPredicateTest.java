package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
            new TagIncludesKeywordsPredicate(tagList);
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_tagDoesNotIncludeKeywords_returnFalse() {
        TagIncludesKeywordsPredicate predicate =
                new TagIncludesKeywordsPredicate(Arrays.asList(KEYWORD_NOT_IN_QUICK_SORT_TAGS));
        assertFalse(predicate.test(QUICK_SORT));
    }
}
