package seedu.ichifund.model.repeater;

import java.util.List;
import java.util.function.Predicate;

import seedu.ichifund.commons.util.StringUtil;

/**
 * Tests that a {@code Repeater}'s {@code Description} matches any of the keywords given.
 */
public class RepeaterDescriptionPredicate implements Predicate<Repeater> {
    private final List<String> keywords;

    public RepeaterDescriptionPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Repeater repeater) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(repeater.getDescription().description, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RepeaterDescriptionPredicate // instanceof handles nulls
                && keywords.equals(((RepeaterDescriptionPredicate) other).keywords)); // state check
    }

}
