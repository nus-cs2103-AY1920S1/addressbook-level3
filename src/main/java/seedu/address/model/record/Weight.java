package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bmi Record's weight in the record book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {

    public static final String MESSAGE_CONSTRAINTS =
        "Weight should only contain numeric characters and at most a single decimal,"
            + " and it should not be blank";
    public static final String VALIDATION_REGEX = "^+?\\d*\\.{0,1}\\d+$";
    private final double weight;

    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        this.weight = Double.parseDouble(weight);
    }

    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Weight // instanceof handles nulls
            && weight == ((Weight) other).weight); // state check
    }

    @Override
    public int hashCode() {
        return ((Double) weight).hashCode();
    }

    @Override
    public String toString() {
        return Double.toString(weight);
    }
}
