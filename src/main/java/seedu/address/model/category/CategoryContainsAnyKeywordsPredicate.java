//@@author ShuTingY
package seedu.address.model.category;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.flashcard.FlashCard;

/**
 * Tests that a {@code FlashCard}'s {@code Category} matches all of the keywords given.
 */
public class CategoryContainsAnyKeywordsPredicate implements Predicate<FlashCard> {
    private final List<String> keywords;

    public CategoryContainsAnyKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(FlashCard flashCard) {
        return keywords
                .stream()
                .anyMatch(keyword -> flashCard.getCategories().contains(new Category(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryContainsAnyKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CategoryContainsAnyKeywordsPredicate) other).keywords)); // state check
    }
}
