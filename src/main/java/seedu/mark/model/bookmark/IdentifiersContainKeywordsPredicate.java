package seedu.mark.model.bookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Bookmark}'s {@code Name} or {@code Url} matches any
 * of the keywords given.
 */
public class IdentifiersContainKeywordsPredicate implements Predicate<Bookmark> {
    private final List<String> keywords;
    private final List<Predicate<Bookmark>> predicates;

    public IdentifiersContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords; // keywords needed for equals(Object) comparison

        this.predicates = new ArrayList<>();
        predicates.add(new NameContainsKeywordsPredicate(keywords));
        predicates.add(new UrlContainsKeywordsPredicate(keywords));
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return predicates.stream()
                .anyMatch(predicate -> predicate.test(bookmark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IdentifiersContainKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IdentifiersContainKeywordsPredicate) other).keywords)); // state check
    }

}
