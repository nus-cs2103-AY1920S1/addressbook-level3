package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's solution in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remark can take any values, and it should not be blank.";
    public static final String DEFAULT_REMARK_STRING = "";
    public static final Remark DEFAULT_REMARK = new Remark();

    /*
     * The first character of the solution must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid solution.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    /**
     * Constructs a {@code Remark} which is empty.
     * Since public constructor of Remark disallows empty remark,
     * this private constructor uses empty string distinguish itself from normal remarks.
     */
    private Remark() {
        value = DEFAULT_REMARK_STRING;
    }

    /**
     * Returns true if a given String matches the default remark String.
     */
    public static boolean isDefaultRemark(String test) {
        return test.equals(DEFAULT_REMARK_STRING);
    }

    /**
     * Returns true if a given {@code Remark} is the default remark.
     */
    public static boolean isDefaultRemark(Remark test) {
        return test == DEFAULT_REMARK;
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
