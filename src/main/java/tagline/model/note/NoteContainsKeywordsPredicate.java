package tagline.model.note;

import java.util.List;
import java.util.function.Predicate;

import tagline.commons.util.StringUtil;

/**
 * Tests that a {@code Note}'s {@code Content} matches any of the keywords given.
 */
public class NoteContainsKeywordsPredicate implements Predicate<Note> {
    private final List<String> keywords;

    public NoteContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    // for testing only, remove after test case works as expected
    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Note note) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsIgnoreCase(note.getTitle().titleDescription, keyword)
                        || StringUtil.containsIgnoreCase(note.getContent().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NoteContainsKeywordsPredicate) other).keywords)); // state check
    }

}
