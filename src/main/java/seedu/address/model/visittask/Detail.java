package seedu.address.model.visittask;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Visit's detail in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class Detail {

    public static final String MESSAGE_CONSTRAINTS = "Details can take any values, and it should not be blank";

    /*
     * The first character of the detail must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = ".*";

    public final String detail;

    /**
     * Constructs a {@code Detail}.
     *
     * @param detail A valid detail.
     */
    public Detail(String detail) {
        requireNonNull(detail);
        checkArgument(isValidDetail(detail), MESSAGE_CONSTRAINTS);
        this.detail = detail;
    }

    /**
     * Returns true if a given string is a valid detail.
     */
    public static boolean isValidDetail(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return detail;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Detail // instanceof handles nulls
                && detail.equals(((Detail) other).detail)); // state check
    }

    @Override
    public int hashCode() {
        return detail.hashCode();
    }

}
