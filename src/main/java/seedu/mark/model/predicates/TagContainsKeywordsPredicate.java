package seedu.mark.model.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;

/**
 * Tests that part of a {@code Bookmark}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Bookmark> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return keywords.stream()
                .anyMatch(keyword -> bookmark.getTags()
                        .stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
