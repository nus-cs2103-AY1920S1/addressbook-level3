package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    //public final String fullDate;
    private LocalDate date;
    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */

    /**
     * Converts String to LocalDate
     * @param date in the format yyyy mm dd.
     */
    public Date(String date) {
        requireNonNull(date);
        //checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        String[] stringDate = date.toString().split(" ");
        this.date = LocalDate.of(Integer.parseInt(stringDate[0]), Integer.parseInt(stringDate[1]),
                Integer.parseInt(stringDate[2]));

    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test == null; // put this for now
        //return test.matches(VALIDATION_REGEX);
    }

    public LocalDate getDate() {
        return date;
    }

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
