package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a SortType in the guiltTrip.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SortType {

    public static final String MESSAGE_CONSTRAINTS =
            "Sort Type should only be either category, amount, description, time or tags";

    /*
     * The first character of the guilttrip must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String[] VALIDATIONLIST = new String[]{"category", "amount", "description" , "time" , "tags"};

    public final String fullType;

    /**
     * Constructs a {@code SortType}.
     *
     * @param fullType A valid sortType.
     */
    public SortType(String fullType) throws IllegalArgumentException {
        requireNonNull(fullType);
        checkArgument(isValidSortType(fullType), MESSAGE_CONSTRAINTS);
        this.fullType = fullType;
    }

    /**
     * Returns true if a given string is a valid sortType.
     */
    public static boolean isValidSortType(String test) {
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
