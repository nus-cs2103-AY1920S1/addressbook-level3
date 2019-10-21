package seedu.billboard.model.expense;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class DateWithinRangePredicate implements Predicate<Expense> {
    public static final String FINDTYPE = "date";
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isWithoutEndDate = false;

    public DateWithinRangePredicate(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateWithinRangePredicate(LocalDateTime startDate) {
        this.startDate = startDate;
        this.endDate = LocalDateTime.now();
        this.isWithoutEndDate = true;
    }

    @Override
    public boolean test(Expense expense) {
        return expense.getCreated().dateTime.isAfter(startDate) && expense.getCreated().dateTime.isBefore(endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateWithinRangePredicate // instanceof handles nulls
                && startDate == ((DateWithinRangePredicate) other).startDate
                && (endDate == ((DateWithinRangePredicate) other).endDate
                || (isWithoutEndDate && ((DateWithinRangePredicate) other).isWithoutEndDate))); // state check
    }

}
