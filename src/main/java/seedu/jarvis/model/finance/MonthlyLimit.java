package seedu.jarvis.model.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents the monthly limit set in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class MonthlyLimit {

    public static final String MESSAGE_CONSTRAINTS =
            "Monthly limit set must have a maximum of 2 decimal places, be a positive value "
                    + "and it should not be blank.";

    public static final String MONEY_CONSTRAINTS =
            "Money spent cannot be equal to or less than 0.";

    public static final String VALIDATION_REGEX = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?";

    private static DecimalFormat df2 = new DecimalFormat("#.00");

    private final double monthlyLimit;

    /**
     * Constructs a {@code MonthlyLimit}.
     *
     * @param value A valid purchase money paid.
     */
    public MonthlyLimit(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        monthlyLimit = Double.parseDouble(value);
    }

    /**
     * Returns true if the given double is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public String getLimitValue() {
        return Double.toString(getMonthlyLimit());
    }

    @Override
    public String toString() {
        return "$" + df2.format(monthlyLimit);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MonthlyLimit
                && monthlyLimit == ((MonthlyLimit) other).monthlyLimit);
    }
}
