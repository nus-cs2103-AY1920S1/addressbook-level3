package seedu.address.model.person.predicates.budget;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.model.person.Budget;

/**
 * Tests that a {@code Budget's}'s {@code Date} matches any of the keywords given.
 */
public class BudgetContainsDatePredicate implements Predicate<Budget> {

    private final LocalDate dateToFilter;

    public BudgetContainsDatePredicate(LocalDate dateToFilter) {
        this.dateToFilter = dateToFilter;
    }

    @Override
    public boolean test(Budget entry) {
        return dateToFilter.equals(entry.getDate().getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetContainsDatePredicate // instanceof handles nulls
                && dateToFilter == (((BudgetContainsDatePredicate) other).dateToFilter)); // state check
    }
}
