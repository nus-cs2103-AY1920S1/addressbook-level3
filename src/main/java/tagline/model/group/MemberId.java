package tagline.model.group;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.AppUtil.checkArgument;

/**
 * Represents a MemberId in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMemberId(String)}
 */
public class MemberId {

    public static final String MESSAGE_CONSTRAINTS = "MemberIds should be numeric";
    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs a {@code MemberId}.
     *
     * @param value A valid tag name.
     */
    public MemberId(String value) {
        requireNonNull(value);
        checkArgument(isValidMemberId(value), MESSAGE_CONSTRAINTS);
        this.value = String.valueOf(Long.valueOf(value));
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidMemberId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberId // instanceof handles nulls
                && value.equals(((MemberId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        String formattedNoteId = String.format("%05d", Long.valueOf(value));
        return '[' + formattedNoteId + ']';
    }

}
