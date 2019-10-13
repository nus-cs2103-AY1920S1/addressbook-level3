package seedu.algobase.model.problem;

import java.util.List;
import java.util.function.Predicate;

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
    private final List<String> keywords;

    public TagIncludesKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    private TagIncludesKeywordsPredicate() {
        this.keywords = null;
    }

    @Override
    public boolean test(Problem problem) {
        return keywords.stream().allMatch(keyword -> problem.getTags().contains(new Tag(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagIncludesKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagIncludesKeywordsPredicate) other).keywords)); // state check
    }
}
