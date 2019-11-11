package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.AppUtil.checkArgument;

/**
 * Represents an Entry's description in GuiltTrip. Guarantees: immutable; is
 * valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should not be blank";

    public final String fullDesc;

    /**
     * Constructs a {@code Description}.
     *
     * @param desc A valid description.
     */
    public Description(String desc) {
        requireNonNull(desc);
        checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        fullDesc = desc;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return !test.trim().isEmpty();
    }

    @Override
    public String toString() {
        return fullDesc;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                        && fullDesc.equals(((Description) other).fullDesc)); // state check
    }

    @Override
    public int hashCode() {
        return fullDesc.hashCode();
    }

}
