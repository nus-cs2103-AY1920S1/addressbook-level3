package seedu.billboard.model.expense;

import java.util.function.Predicate;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class AmountWithinRangePredicate implements Predicate<Expense> {
    public static final String FINDTYPE = "amt";
    private Amount lowerLimit;
    private Amount upperLimit;

    public AmountWithinRangePredicate(Amount lowerLimit, Amount upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public AmountWithinRangePredicate(Amount lowerLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = new Amount("9999999.99");
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

}
