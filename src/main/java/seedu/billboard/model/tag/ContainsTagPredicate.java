package seedu.billboard.model.tag;

import java.util.Set;
import java.util.function.Predicate;

import seedu.billboard.model.expense.Expense;

/**
 * Tests that a {@code Expense}'s {@code Tags} matches any of the keywords given.
 */
public class ContainsTagPredicate implements Predicate<Expense> {
    private final Set<Tag> tags;

    public ContainsTagPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Expense expense) {
        return tags.stream()
                .anyMatch(tag -> expense.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsTagPredicate // instanceof handles nulls
                && tags.equals(((ContainsTagPredicate) other).tags)); // state check
    }
}
