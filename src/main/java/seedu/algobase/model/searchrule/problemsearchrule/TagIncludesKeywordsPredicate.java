package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.isTwoListsEqual;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;

/**
 * Tests that the given set of keywords is a subset of a {@code Problem}'s set of {@code Tag}.
 */
public class TagIncludesKeywordsPredicate implements Predicate<Problem> {

    public static final TagIncludesKeywordsPredicate DEFAULT_TAG_PREDICATE =
        new TagIncludesKeywordsPredicate() {
            @Override
            public boolean test(Problem problem) {
                return true;
            }
        };
    private final List<Keyword> keywords;

    public TagIncludesKeywordsPredicate(List<Keyword> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    private TagIncludesKeywordsPredicate() {
        this.keywords = null;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Problem problem) {
        requireNonNull(problem);
        if (keywords.stream().anyMatch(
            keyword -> (!Tag.isValidTagName(keyword.toString()) || keyword.equals("#forRefresh#")))) {
            return false;
        }
        return keywords.stream().allMatch(keyword -> problem.getTags().contains(new Tag(keyword.toString())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagIncludesKeywordsPredicate // instanceof handles nulls
                && isTwoListsEqual(keywords, ((TagIncludesKeywordsPredicate) other).keywords)); // state check
    }
}
