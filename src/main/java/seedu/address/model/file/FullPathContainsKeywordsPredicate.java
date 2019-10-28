package seedu.address.model.file;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that an {@code EncryptedFile}'s {@code FullPath} matches any of the keywords given.
 */
public class FullPathContainsKeywordsPredicate implements Predicate<EncryptedFile> {
    private final List<String> keywords;

    public FullPathContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(EncryptedFile file) {
        return keywords.stream()
                .anyMatch(keyword -> file.getFullPath().toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FullPathContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FullPathContainsKeywordsPredicate) other).keywords)); // state check
    }

}
