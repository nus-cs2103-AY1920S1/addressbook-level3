package seedu.billboard.model.expense;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class DateWithinRangePredicate implements Predicate<Expense> {
    private CreatedDateTime startDate;
    private CreatedDateTime endDate;

    public DateWithinRangePredicate(CreatedDateTime startDate, CreatedDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Expense expense) {
        boolean isBefore = true;
        boolean isAfter = true;

        if (startDate != null) {
            isAfter = expense.getCreated().dateTime.isEqual(startDate.dateTime)
                    || expense.getCreated().dateTime.isAfter(startDate.dateTime);
        }
        if (endDate != null) {
            isBefore = expense.getCreated().dateTime.isEqual(endDate.dateTime)
                    || expense.getCreated().dateTime.isBefore(endDate.dateTime);
        }
        return isBefore && isAfter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateWithinRangePredicate // instanceof handles nulls
                && startDate == ((DateWithinRangePredicate) other).startDate
                && (endDate == ((DateWithinRangePredicate) other).endDate)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
