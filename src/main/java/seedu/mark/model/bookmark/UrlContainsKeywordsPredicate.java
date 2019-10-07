package seedu.mark.model.bookmark;

import java.util.List;
import java.util.function.Predicate;

import seedu.mark.commons.util.StringUtil;

/**
 * Tests that a {@code Bookmark}'s {@code Url} matches any part of the keywords given.
 */
public class UrlContainsKeywordsPredicate implements Predicate<Bookmark> {
    private final List<String> keywords;

    public UrlContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(bookmark.getUrl().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UrlContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((UrlContainsKeywordsPredicate) other).keywords)); // state check
    }

}
