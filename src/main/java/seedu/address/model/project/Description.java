package seedu.address.model.project;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Description {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CONSTRAINTS =
            "Description should only contain alphanumeric characters and spaces, and it should not be blank";

    public final String description;

    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDesc = (Description) other;
        return otherDesc.description.equals(this.description);
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
