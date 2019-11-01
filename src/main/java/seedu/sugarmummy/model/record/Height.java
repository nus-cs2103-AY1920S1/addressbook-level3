package seedu.sugarmummy.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bmi Record's height in the record book. Guarantees: immutable; is valid as declared in {@link
 * #isValidHeight(String)}
 */
public class Height {

    public static final String MESSAGE_CONSTRAINTS =
            "Height should only contain positive real number";
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

    public double getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
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
