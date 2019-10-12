package seedu.address.model.person.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of a Loan.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description cannot be only whitespaces or over 140 characters long.";
    public static final long MAX_DESCRIPTION_LENGTH = 140;

    public String description;

    /**
     * Constructs a {@code description}.
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if the given string is a valid description.
     * @param testDescription The string to test for validity.
     * @return True if valid (not whitespace and <= 140 characters), false otherwise.
     */
    public static boolean isValidDescription(String testDescription) {
        return testDescription.trim().length() > 0
                && testDescription.length() <= MAX_DESCRIPTION_LENGTH;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return description.equals(otherDescription.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
