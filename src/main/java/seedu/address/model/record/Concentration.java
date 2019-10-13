package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Blood Sugar Record's sugar concentration in the record book.
 * Guarantees: immutable; is valid as declared in {@link #isValidConcentration(String)}
 */
public class Concentration {

    public static final String MESSAGE_CONSTRAINTS =
        "Concentration should only contain numeric characters and at most a single decimal,"
            + " and it should not be blank";
    public static final String VALIDATION_REGEX = "^+?\\d*\\.{0,1}\\d+$";
    private final double concentration;

    public Concentration(String concentration) {
        requireNonNull(concentration);
        checkArgument(isValidConcentration(concentration), MESSAGE_CONSTRAINTS);
        this.concentration = Double.parseDouble(concentration);
    }

    public static boolean isValidConcentration(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Weight // instanceof handles nulls
            && concentration == ((Concentration) other).concentration); // state check
    }

    @Override
    public int hashCode() {
        return ((Double) concentration).hashCode();
    }

    @Override
    public String toString() {
        return Double.toString(concentration);
    }
}
