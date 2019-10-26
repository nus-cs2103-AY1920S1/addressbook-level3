package seedu.billboard.model.expense;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class AmountWithinRangePredicate implements Predicate<Expense> {
    private Amount lowerLimit;
    private Amount upperLimit;

    public AmountWithinRangePredicate(Amount lowerLimit, Amount upperLimit) {
        this.lowerLimit = lowerLimit != null ? lowerLimit : new Amount("0");
        this.upperLimit = upperLimit != null ? upperLimit : new Amount("9999999999.99");
    }

    @Override
    public boolean test(Expense expense) {
        return expense.getAmount().amount.compareTo(lowerLimit.amount) >= 0
                && expense.getAmount().amount.compareTo(upperLimit.amount) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AmountWithinRangePredicate // instanceof handles nulls
                && lowerLimit == ((AmountWithinRangePredicate) other).lowerLimit
                && upperLimit == ((AmountWithinRangePredicate) other).upperLimit); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerLimit, upperLimit);
    }
}
