package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTime;

    /**
     * Constructs a {@code Name}.
     *
     * @param desc A valid name.
     */
    public Time(String time) {
        requireNonNull(time);
        //checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        fullTime = time;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    //public static boolean isValidDescription(String test) {
    //    return test.matches(VALIDATION_REGEX);
    //}


    @Override
    public String toString() {
        return fullTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && fullTime.equals(((Time) other).fullTime)); // state check
    }

    @Override
    public int hashCode() {
        return fullTime.hashCode();
    }

}
