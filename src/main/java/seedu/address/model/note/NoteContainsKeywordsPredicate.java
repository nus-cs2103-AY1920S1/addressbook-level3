package seedu.address.model.note;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Note}'s {@code Description} {@code Title} {@code Content} matches any of the keywords given.
 */
public class NoteContainsKeywordsPredicate implements Predicate<Note> {
    private final List<String> keywords;

    public NoteContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Note note) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(note.getTitle().title, keyword)
                        || StringUtil.containsStringIgnoreCase(note.getDescription().description, keyword)
                        || StringUtil.containsStringIgnoreCase(note.getContent().content, keyword)
                        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NoteContainsKeywordsPredicate) other).keywords)); // state check
    }

}
