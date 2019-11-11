package seedu.address.model.card;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Card}'s {@code Word} contains any of the keywords given.
 * Keyword "HE" will match all {"he", "HeArt", "HeAVEN", "NEW HeaVEN"}.
 */
public class WordContainsKeywordsPredicate implements Predicate<Card> {
    private final List<String> keywords;

    public WordContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Card card) {
        return wordTest(card) || meaningTest(card) || tagTest(card); // Short circuits early
    }

    /**
     * Checks if the word contains the key word.
     *
     * @param card to be tested.
     * @return true or false.
     */
    private boolean wordTest(Card card) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubWordIgnoreCase(card.getWord().getValue(), keyword));
    }

    /**
     * Checks if the meaning contains the key word.
     *
     * @param card to be tested.
     * @return true or false.
     */
    private boolean meaningTest(Card card) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubWordIgnoreCase(card.getMeaning().getValue(), keyword));
    }

    /**
     * Checks if the tag contains the key word.
     *
     * @param card to be tested.
     * @return true or false.
     */
    private boolean tagTest(Card card) {
        List<String> tagStringsToTest = new ArrayList<>();

        for (Tag tag : card.getTags()) {
            tagStringsToTest.add(tag.getTagName());
        }

        for (String s : tagStringsToTest) {
            if (keywords.stream().anyMatch(keyword -> StringUtil.containsSubWordIgnoreCase(s, keyword))) {
                return true; // Short circuit
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((WordContainsKeywordsPredicate) other).keywords)); // state check
    }

}
