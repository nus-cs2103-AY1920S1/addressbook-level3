package seedu.address.model.currency;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Generic abstraction of exchange rate.
 */
public class Rate {
    public static final String MESSAGE_CONSTRAINTS = "Rate can take any positive numerical value with"
            + " no more than 2 decimal places, and it should not be blank";

    public static final String VALIDATION_REGEX = "^[+]?[0-9]+([.][0-9]{1,2})?$";

    public final Double value;

    /**
     * Constructs an {@code Rate}.
     *
     * @param value A valid rate.
     */
    public Rate(String value) {
        requireNonNull(value);
        checkArgument(isValidRate(value), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(value);
    }

    /**
     * Returns true if a given string is a valid rate.
     */
    public static boolean isValidRate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rate // instanceof handles nulls
                && value.equals(((Rate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
