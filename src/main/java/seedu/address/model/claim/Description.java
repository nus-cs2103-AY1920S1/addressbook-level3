package seedu.address.model.claim;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Claim's description in Contact.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description of claim should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String text;

    /**
     * Constructs a {@code Description}.
     *
     * @param text
     */
    public Description(String text) {
        requireNonNull(text);
        checkArgument(isValidDescription(text), MESSAGE_CONSTRAINTS);
        this.text = text;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Description // instanceof handles nulls
                && text.equals(((Description) obj).text)); // state check
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
