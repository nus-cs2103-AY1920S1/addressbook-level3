package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's start age elibility in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class StartAge {

    public static final String MESSAGE_CONSTRAINTS =
            "Ages should only contain numbers, cannot be blank and cannot exceed 120.";
    public static final String DATA_TYPE = "START AGE";
    public static final String MESSAGE_NO_MINIMUM_AGE = "No minimum age";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{N}]+";
    public static final String AGE_ZERO = "0";

    public final String age;
    /**
     * Constructs a {@code StartAge}.
     *
     * @param age A valid age.
     */
    public StartAge(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        this.age = age;
    }

    /**
     * Constructs a {@code StartAge} starting from 0.
     *
     */
    public StartAge() {
        this.age = AGE_ZERO;
    }

    /**
     * Returns true if a given string is a valid coverage declaration.
     */
    public static boolean isValidAge(String test) {
        return test.matches(VALIDATION_REGEX) && Integer.parseInt(test) <= Integer.parseInt(EndAge.AGE_INFINITY);
    }

    @Override
    public String toString() {
        return age;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartAge // instanceof handles nulls
                && age.equals(((StartAge) other).age)); // state check
    }

    @Override
    public int hashCode() {
        return age.hashCode();
    }

}
