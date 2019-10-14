package seedu.billboard.model.tag;

import java.util.List;
import java.util.function.Predicate;

import seedu.billboard.model.expense.Expense;

public class ContainsTagPredicate implements Predicate<Expense> {
    private final List<Tag> tags;

    public ContainsTagPredicate(List<Tag> tags) {
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
