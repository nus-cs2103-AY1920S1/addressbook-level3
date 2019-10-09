package seedu.address.model.flashcard;

import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

public class RatingContainsKeywordPredicate implements Predicate<FlashCard> {

    private final String keyword;

    public RatingContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(FlashCard flashCard) {
        return StringUtil.containsWordIgnoreCase(flashCard.getRating().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingContainsKeywordPredicate // instance of handles nulls
                && keyword.equals(((RatingContainsKeywordPredicate) other).keyword)); // state check
    }
}
