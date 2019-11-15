package dukecooks.model.health.components;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents a Remark in Health Records.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRemarkName(String)}
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remarks should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String remarkName;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remarkName A valid remark name.
     */
    public Remark(String remarkName) {
        requireNonNull(remarkName);
        AppUtil.checkArgument(isValidRemarkName(remarkName), MESSAGE_CONSTRAINTS);
        this.remarkName = remarkName;
    }

    /**
     * Returns true if a given string is a valid remark name.
     */
    public static boolean isValidRemarkName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && remarkName.equals(((Remark) other).remarkName)); // state check
    }

    @Override
    public int hashCode() {
        return remarkName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return remarkName;
    }

}
