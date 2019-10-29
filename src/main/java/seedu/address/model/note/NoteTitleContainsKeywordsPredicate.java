package seedu.address.model.note;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 *  Tests that a {@code Note}'s {@code Title} matches any of the keywords given.
 */
public class NoteTitleContainsKeywordsPredicate implements Predicate<Note> {
    private final List<String> keywords;

    public NoteTitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Note note) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(note.getTitle().fullTitle, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteTitleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NoteTitleContainsKeywordsPredicate) other).keywords)); // state check
    }
}
