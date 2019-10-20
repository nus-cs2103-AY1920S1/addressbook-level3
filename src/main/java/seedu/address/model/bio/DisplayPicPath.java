package seedu.address.model.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's displayPicPath in the displayPicPath book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDisplayPicPath(String)}
 */
public class DisplayPicPath {

    public static final String MESSAGE_CONSTRAINTS = "Display picture path can take any values that represents a valid "
            + "path to an image.";

    /*
     * The first character of the displayPicPath must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^$|[^\\s].*";

    public final String displayPicPath;

    /**
     * Constructs an {@code DisplayPicPath}.
     *
     * @param displayPicPath A valid displayPicPath.
     */
    public DisplayPicPath(String displayPicPath) {
        requireNonNull(displayPicPath);
        checkArgument(isValidDisplayPicPath(displayPicPath), MESSAGE_CONSTRAINTS);
        this.displayPicPath = displayPicPath;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidDisplayPicPath(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return displayPicPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPicPath // instanceof handles nulls
                && displayPicPath.equals(((DisplayPicPath) other).displayPicPath)); // state check
    }

    @Override
    public int hashCode() {
        return displayPicPath.hashCode();
    }

}
