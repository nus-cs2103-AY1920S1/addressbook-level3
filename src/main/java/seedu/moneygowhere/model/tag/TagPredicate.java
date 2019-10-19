package seedu.moneygowhere.model.tag;

import java.util.function.Predicate;

import seedu.moneygowhere.model.spending.Spending;

/**
 * Tests that a {@code Spending}'s {@code Tag} matches any of the keywords given.
 */
public class TagPredicate implements Predicate<Spending> {
    private final String tag;

    public TagPredicate(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Spending spending) {
        return spending.getTags().stream().anyMatch(s -> s.tagName.equalsIgnoreCase(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagPredicate // instanceof handles nulls
                && tag.equals(((TagPredicate) other).tag)); // state check
    }

}
