package tagline.model.contact;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values";

    /*
     * The address value can be anything, including empty string.
     */
    public static final String VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        description = description.trim();
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
