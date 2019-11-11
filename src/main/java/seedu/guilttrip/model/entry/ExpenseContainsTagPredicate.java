package seedu.guilttrip.model.entry;

import java.util.Set;
import java.util.function.Predicate;

import seedu.guilttrip.model.tag.Tag;

/**
 * Tests that a {@code Expense}'s {@code Tag} matches any of the keywords given.
 */
public class ExpenseContainsTagPredicate implements Predicate<Expense> {
    private final Set<Tag> tags;

    public ExpenseContainsTagPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        return tags;
    }
    @Override
    public boolean test(Expense entry) {
        return tags.stream()
                .anyMatch(tag -> entry.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseContainsTagPredicate // instanceof handles nulls
                && tags.equals(((ExpenseContainsTagPredicate) other).tags)); // state check
    }

}
