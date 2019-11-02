package dukecooks.model.profile.person;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;
import dukecooks.model.Date;

/**
 * Represents the user's date of birth.
 */
public class DoB {
    public static final String MESSAGE_CONSTRAINTS =
            "DoB should only contain numeric characters in the format of DD/MM/YYYY, and it should not be blank";

    public final Date dateOfBirth;

    /**
     * Constructs a {@code date}.
     *
     * @param date A valid date in String form.
     */
    public DoB(String date) {
        requireNonNull(date);
        AppUtil.checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        dateOfBirth = Date.generateDate(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        return Date.isValidDateFormat(date);
    }

    @Override
    public String toString() {
        return dateOfBirth.toString();
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
