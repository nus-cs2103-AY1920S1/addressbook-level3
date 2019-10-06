package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class VisitList {
    public final String value;
    public static final String MESSAGE_CONSTRAINTS = "Visit date should follow dd/mm/yyyy format";
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";

    public VisitList(String date) {
        requireNonNull(date);
        value = date;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidVisitDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitList // instanceof handles nulls
                && value.equals(((VisitList) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
