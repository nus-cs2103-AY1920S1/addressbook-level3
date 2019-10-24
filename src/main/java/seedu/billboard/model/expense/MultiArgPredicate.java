package seedu.billboard.model.expense;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class MultiArgPredicate implements Predicate<Expense> {
    private Set<Predicate<Expense>> predicateSet;

    public MultiArgPredicate() {
        predicateSet = new HashSet<>();
    }

    @Override
    public boolean test(Expense expense) {
        return predicateSet.stream().allMatch(x -> x.test(expense));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MultiArgPredicate // instanceof handles nulls
                && predicateSet.equals(((MultiArgPredicate) other).predicateSet)); // state check
    }

    public void setKeywords(List<String> keywords) {
        predicateSet.add(new AllContainsKeywordsPredicate(keywords));
    }

    public void setDateRange(CreatedDateTime startDate, CreatedDateTime endDate) {
        predicateSet.add(new DateWithinRangePredicate(startDate, endDate));
    }

    public void setAmtRange(Amount lowerAmtLimit, Amount upperAmtLimit) {
        predicateSet.add(new AmountWithinRangePredicate(lowerAmtLimit, upperAmtLimit));
    }
}
