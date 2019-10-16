package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class SortType {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullType;

    /**
     * Constructs a {@code Name}.
     *
     * @param desc A valid name.
     */
    public SortType(String fullType) {
        requireNonNull(fullType);
        checkArgument(isValidDescription(fullType), MESSAGE_CONSTRAINTS);
        this.fullType = fullType;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortType // instanceof handles nulls
                && fullType.equals(((SortType) other).fullType)); // state check
    }

    @Override
    public int hashCode() {
        return fullType.hashCode();
    }

}
