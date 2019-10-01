package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's start age elibility in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class EndAge {

    public static final String MESSAGE_CONSTRAINTS =
            "Ages should only contain numbers.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{N}] ]*";

    public final String age;
    /**
     * Constructs a {@code EndAge}.
     *
     * @param age A valid age.
     */
    public EndAge(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        this.age = age;
    }

    /**
     * Returns true if a given string is a valid coverage declaration.
     */
    public static boolean isValidAge(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return age;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndAge // instanceof handles nulls
                && age.equals(((EndAge) other).age)); // state check
    }

    @Override
    public int hashCode() {
        return age.hashCode();
    }

}
