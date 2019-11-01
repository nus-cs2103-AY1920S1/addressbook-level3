package seedu.sugarmummy.model.bio;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's name in the address book. Guarantees: immutable; is valid as declared in {@link
 * #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Name should only contain alphabets and spaces, and it should not be blank.";

    public static final String VALIDATION_REGEX = "^[A-Za-z\\s]+";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return !test.isEmpty() && test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
