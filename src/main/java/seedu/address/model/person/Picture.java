package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPicture(String)}
 */
public class Picture {

    public static final String MESSAGE_CONSTRAINTS = "Picture should be in .jpg/.png/.gif/.bmp format and be in the "
            + "same directory as TutorAid";

    public static final String VALIDATION_REGEX = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param picture A valid email address.
     */
    public Picture(String picture) {
        requireNonNull(picture);
        checkArgument(isValidPicture(picture), MESSAGE_CONSTRAINTS);
        value = picture;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidPicture(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Picture // instanceof handles nulls
                && value.equals(((Picture) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
