package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Card's meaning.
 * Guarantees: immutable; is valid as declared in {@link #isValidMeaning(String)}
 */
public class Meaning {

    public static final int MAX_LEN = 512;
    public static final String MESSAGE_CONSTRAINTS =
            "Meanings should be 1-" + MAX_LEN + " characters long, and not all are white spaces.";

    /*
     * The description should contain 1-MAX_LEN characters, and not all are white spaces.
     */
    public static final String VALIDATION_REGEX = "^(?=.*\\S).{1," + MAX_LEN + "}$";

    public final String value;

    /**
     * Constructs a {@code Meaning}.
     *
     * @param meaning A valid meaning.
     */
    public Meaning(String meaning) {
        requireNonNull(meaning);
        checkArgument(isValidMeaning(meaning), MESSAGE_CONSTRAINTS);
        this.value = meaning;
    }

    /**
     * Returns true if a given string is a valid meaning.
     */
    public static boolean isValidMeaning(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meaning // instanceof handles nulls
                && value.equals(((Meaning) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
