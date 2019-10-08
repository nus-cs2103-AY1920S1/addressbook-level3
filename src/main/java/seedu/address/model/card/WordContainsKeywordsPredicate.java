package seedu.address.model.card;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Card}'s {@code Word} contains any of the keywords given.
 * Keyword "HE" will match all {"he", "HeArt", "HeAVEN", "NEW HeaVEN"
 */
public class WordContainsKeywordsPredicate implements Predicate<Card> {
    private final List<String> keywords;

    public WordContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Card card) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.startsWithIgnoreCase(card.getWord().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((WordContainsKeywordsPredicate) other).keywords)); // state check
    }

}
