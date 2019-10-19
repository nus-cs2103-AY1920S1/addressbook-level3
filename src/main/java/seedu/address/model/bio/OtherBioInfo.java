package seedu.address.model.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a OtherBioInfo in the user's biography data.
 * Guarantees: immutable; name is valid as declared in {@link #isValidOtherInfo(String)}
 */
public class OtherBioInfo {

    public static final String MESSAGE_CONSTRAINTS =
            "OtherBioInfo can take any values.";
    public static final String VALIDATION_REGEX = "\"^$|[^\\\\s].*";

    public final String otherInfo;

    /**
     * Constructs a {@code OtherBioInfo}.
     *
     * @param otherInfo A valid otherInfo.
     */
    public OtherBioInfo(String otherInfo) {
        requireNonNull(otherInfo);
        checkArgument(isValidOtherInfo(otherInfo), MESSAGE_CONSTRAINTS);
        this.otherInfo = otherInfo;
    }

    /**
     * Returns true if a given string is a valid otherInfo.
     */
    public static boolean isValidOtherInfo(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OtherBioInfo // instanceof handles nulls
                && otherInfo.equals(((OtherBioInfo) other).otherInfo)); // state check
    }

    @Override
    public int hashCode() {
        return otherInfo.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return otherInfo;
    }

}
