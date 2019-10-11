package seedu.weme.model.meme;

import java.util.List;
import java.util.function.Predicate;

import seedu.weme.commons.util.StringUtil;

/**
 * Tests that a {@code Meme}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Meme> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Meme meme) {
        return keywords.stream()
                .anyMatch(keyword ->
                        meme.getTags().stream()
                                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))
                );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
