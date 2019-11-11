package seedu.sugarmummy.model.biography;

import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's additional biography information in his / her biography. Guarantees: immutable; name
 * is valid as
 * declared in {@link
 * #isValidOtherBioInfo(String)}
 */
public class OtherBioInfo {

    public static final String MESSAGE_CONSTRAINTS =
            "Other Bio Info can take any values.";
    public static final String VALIDATION_REGEX = "^$|[^\\\\s].*";

    public final String otherInfo;

    /**
     * Constructs a {@code OtherBioInfo}.
     *
     * @param otherInfo A valid otherInfo.
     */
    public OtherBioInfo(String otherInfo) {
        checkArgument(isValidOtherBioInfo(otherInfo), MESSAGE_CONSTRAINTS);
        this.otherInfo = otherInfo;
    }

    /**
     * Returns true if a given string is a valid otherInfo.
     */
    public static boolean isValidOtherBioInfo(String test) {
        boolean temp = test.matches(VALIDATION_REGEX);
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
