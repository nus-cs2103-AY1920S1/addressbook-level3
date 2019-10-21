package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a SortType in the finance manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SortType {

    public static final String MESSAGE_CONSTRAINTS =
            "Sort Type should only be either amount, description, time or tags";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String[] VALIDATIONLIST = new String[]{"amount", "description" , "time" , "tags"};

    public final String fullType;

    /**
     * Constructs a {@code Name}.
     *
     * @param desc A valid name.
     */
    public SortType(String fullType) throws IllegalArgumentException {
        requireNonNull(fullType);
        checkArgument(isValidDescription(fullType), MESSAGE_CONSTRAINTS);
        this.fullType = fullType;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return Arrays.stream(VALIDATIONLIST).anyMatch(t -> t.equalsIgnoreCase(test));
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
