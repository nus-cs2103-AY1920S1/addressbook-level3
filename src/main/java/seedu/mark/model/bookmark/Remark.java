package seedu.mark.model.bookmark;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bookmark's remark in Mark.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String INVALID_CHARACTER = "/";

    public static final String MESSAGE_CONSTRAINTS = "Remarks can contain any characters except " + INVALID_CHARACTER;

    /*
     * The first character of the remark must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     *
     * The rest of the remark can contain any character except the invalid character.
     */
    public static final String VALIDATION_REGEX = "[^\\s][^" + INVALID_CHARACTER + "]*";

    public static final String DEFAULT_VALUE = "-";
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is an empty remark.
     */
    public static boolean isEmptyRemark(String test) {
        return test.trim().equals("");
    }

    /**
     * Returns a {@code Remark} with the default value.
     */
    public static Remark getDefaultRemark() {
        return new Remark(DEFAULT_VALUE);
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
