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

    /**
     * Set the keywords for the predicate used to filter through expenses. List should contain at least 1 keyword.
     * @param keywords List of keywords used to filter through expenses.
     */
    public void setKeywords(List<String> keywords) {
        predicateSet.add(new AllContainsKeywordsPredicate(keywords));
    }

    /**
     * Set the date range limit for the predicate used to filter through expenses. Start date and end date cannot
     * both be null.
     *
     * @param startDate Start date limit, null if only have end date.
     * @param endDate End date limit, null if only have start date.
     */
    public void setDateRange(CreatedDateTime startDate, CreatedDateTime endDate) {
        predicateSet.add(new DateWithinRangePredicate(startDate, endDate));
    }

    /**
     * Set the amount range limit for the predicate used to filter through expenses. Lower amount limit
     * and upper amount limit cannot both be null.
     *
     * @param lowerAmtLimit Lower amount limit, null if only have upper amount limit.
     * @param upperAmtLimit Upper amount limit, null if only have lower amount limit.
     */
    public void setAmtRange(Amount lowerAmtLimit, Amount upperAmtLimit) {
        predicateSet.add(new AmountWithinRangePredicate(lowerAmtLimit, upperAmtLimit));
    }
}
