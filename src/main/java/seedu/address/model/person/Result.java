package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidResult(String)}
 */
public class Result {

    public static final String MESSAGE_CONSTRAINTS = "Result should be a number";

    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param result A valid email address.
     */
    public Result(String result) {
        requireNonNull(result);
        checkArgument(isValidResult(result), MESSAGE_CONSTRAINTS);
        if (Integer.parseInt(result) > 100 ) {
            //throw exception here
        }
        value = result;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidResult(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Result // instanceof handles nulls
                && value.equals(((Result) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
