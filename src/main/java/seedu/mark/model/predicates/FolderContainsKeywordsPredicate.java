package seedu.mark.model.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;

/**
 * Tests that part of a {@code Bookmark}'s {@code Folder} matches any of the keywords given.
 */
public class FolderContainsKeywordsPredicate implements Predicate<Bookmark> {
    private final List<String> keywords;

    public FolderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return keywords.stream()
                .anyMatch(keyword -> bookmark.getFolder().folderName.equalsIgnoreCase(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FolderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FolderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
