package seedu.moneygowhere.model.spending;

import java.util.function.Predicate;

/**
 * Tests that a {@code Spending}'s {@code Date} matches any of the keywords given.
 */
public class DateInRangePredicate implements Predicate<Spending> {
    private final Date startDate;
    private final Date endDate;

    public DateInRangePredicate(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Spending spending) {
        return spending.getDate().compareTo(startDate) >= 0 && spending.getDate().compareTo(endDate) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateInRangePredicate // instanceof handles nulls
                && startDate.equals(((DateInRangePredicate) other).startDate)
                && endDate.equals(((DateInRangePredicate) other).endDate)); // state check
    }

}
