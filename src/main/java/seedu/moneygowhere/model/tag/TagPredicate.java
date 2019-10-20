package seedu.moneygowhere.model.tag;

import java.util.Set;
import java.util.function.Predicate;

import seedu.moneygowhere.model.spending.Spending;

/**
 * Tests that a {@code Spending}'s {@code Tag} matches any of the keywords given.
 */
public class TagPredicate implements Predicate<Spending> {
    private final Set<String> tags;

    public TagPredicate(Set<String> tag) {
        this.tags = tag;
    }

    @Override
    public boolean test(Spending spending) {
        for (Tag spendingTag : spending.getTags()) {
            if (tags.contains(spendingTag.tagName.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagPredicate // instanceof handles nulls
                && tags.equals(((TagPredicate) other).tags)); // state check
    }

}
