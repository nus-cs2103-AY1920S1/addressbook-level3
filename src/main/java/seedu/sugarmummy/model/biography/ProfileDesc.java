package seedu.sugarmummy.model.biography;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's profile description in his / her biography. Guarantees: immutable; name is valid as declared in
 * {@link
 * #isValidProfileDesc(String)}
 */
public class ProfileDesc {

    public static final String MESSAGE_CONSTRAINTS =
            "Profile description can take any values.";
    public static final String VALIDATION_REGEX = "^$|[^\\s].*";

    public final String profileDesc;

    /**
     * Constructs a {@code ProfileDesc}.
     *
     * @param profileDesc A valid profileDesc.
     */
    public ProfileDesc(String profileDesc) {
        requireNonNull(profileDesc);
        checkArgument(isValidProfileDesc(profileDesc), MESSAGE_CONSTRAINTS);
        this.profileDesc = profileDesc;
    }

    /**
     * Returns true if a given string is a valid profileDesc.
     */
    public static boolean isValidProfileDesc(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfileDesc // instanceof handles nulls
                        && profileDesc.equals(((ProfileDesc) other).profileDesc)); // state check
    }

    @Override
    public int hashCode() {
        return profileDesc.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return profileDesc;
    }

}
