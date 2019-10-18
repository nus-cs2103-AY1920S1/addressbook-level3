package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bmi Record's height in the record book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {

    public static final String MESSAGE_CONSTRAINTS =
        "Height should only contain numeric characters and at most a single decimal,"
            + " and it should not be blank";
    public static final String VALIDATION_REGEX = "^+?\\d*\\.{0,1}\\d+$";
    private final double height;

    public Height(String height) {
        requireNonNull(height);
        checkArgument(isValidHeight(height), MESSAGE_CONSTRAINTS);
        this.height = Double.parseDouble(height);
    }

    public static boolean isValidHeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Weight // instanceof handles nulls
            && height == ((Height) other).height); // state check
    }

    @Override
    public int hashCode() {
        return ((Double) height).hashCode();
    }

    @Override
    public String toString() {
        return Double.toString(height);
    }
}
