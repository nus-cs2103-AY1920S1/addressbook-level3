package seedu.ichifund.model.loans;

import static java.util.Objects.requireNonNull;

/**
 * Name type for Loans.
 */
public class Name {
    public static final String NAMEREGEX = "()"; // '.' followed by exactly two numerical digits

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String fullName;

    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.ichifund.model.person.Name // instanceof handles nulls
                && fullName.equals(((seedu.ichifund.model.person.Name) other).fullName)); // state check
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * To check if boolean condition is fulfilled.
     *
     * @param condition
     * @param errorMessage
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
