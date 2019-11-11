package seedu.address.model.note;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Notes}'s {@code ModuleCode} matches any of the keywords given.
 */
public class NotesContainsKeywordsPredicate implements Predicate<Notes> {
    private final List<String> keywords;

    public NotesContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Notes note) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(note.getCode().value + " "
                        + note.getType().type + " "
                        + note.getContent().content, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotesContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NotesContainsKeywordsPredicate) other).keywords)); // state check
    }
}
