package seedu.billboard.model.expense;

import java.util.function.Predicate;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class AmountWithinRangePredicate implements Predicate<Expense> {
    public static final String FINDTYPE = "amt";
    private float lowerLimit;
    private float upperLimit;

    public AmountWithinRangePredicate(float lowerLimit, float upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public AmountWithinRangePredicate(float lowerLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = Float.POSITIVE_INFINITY;
    }

    @Override
    public boolean test(Expense expense) {
        return expense.getAmount().amount >= lowerLimit && expense.getAmount().amount <= upperLimit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AmountWithinRangePredicate // instanceof handles nulls
                && lowerLimit == ((AmountWithinRangePredicate) other).lowerLimit
                && upperLimit == ((AmountWithinRangePredicate) other).upperLimit); // state check
    }

}
