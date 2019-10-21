package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a SortSequence in the finance manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SortSequence {

    public static final String MESSAGE_CONSTRAINTS =
            "SortSequence should only be ascending or descending";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String sequence;

    /**
     * Constructs a {@code Name}.
     *
     * @param desc A valid name.
     */
    public SortSequence(String sequence) {
        requireNonNull(sequence);
        checkArgument(isValidDescription(sequence), MESSAGE_CONSTRAINTS);
        this.sequence = sequence;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return (test.equalsIgnoreCase("ascending") || test.equalsIgnoreCase("descending"));
    }

    public int getSequence() {
        if (this.sequence.equalsIgnoreCase("ascending")) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return sequence;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortSequence // instanceof handles nulls
                && sequence.equals(((SortSequence) other).sequence)); // state check
    }

    @Override
    public int hashCode() {
        return sequence.hashCode();
    }

}
