package seedu.address.profile.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.DateParser;

/**
 * Represents the user's date of birth.
 */
public class DoB {
    public static final String MESSAGE_CONSTRAINTS =
            "DoB should only contain numeric characters in the format of DD/MM/YYYY, and it should not be blank";

    public final String dateOfBirth;

    /**
     * Constructs a {@code date}.
     *
     * @param date A valid date in String form.
     */
    public DoB(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        dateOfBirth = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        return DateParser.isValidDate(date);
    }

    @Override
    public String toString() {
        return dateOfBirth;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoB // instanceof handles nulls
                && dateOfBirth.equals(((DoB) other).dateOfBirth)); // state check
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }

}
