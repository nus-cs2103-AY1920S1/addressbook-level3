package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Card's meaning.
 * Guarantees: immutable; is valid as declared in {@link #isValidMeaning(String)}
 */
public class Meaning {

    /** Maximum length of Meanings's string is set at 200 characters to ensure Hints showing properly. */
    public static final int MAX_LEN = 200;

    public static final String MESSAGE_CONSTRAINTS =
            "Meanings should be 1-" + MAX_LEN + " characters long, and not all are white spaces.";

    /*
     * The description should contain 1-MAX_LEN characters, and not all are white spaces.
     */
    private static final String VALIDATION_REGEX = "^(?=.*\\S).{1," + MAX_LEN + "}$";

    private final String value;

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
     * Constructs a {@code Meaning}.
     *
     * @param meaning A valid meaning.
     */
    public Meaning(String meaning, boolean isByPass) {
        requireNonNull(meaning);
        if (!isByPass) {
            checkArgument(isValidMeaning(meaning), MESSAGE_CONSTRAINTS);
        }
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

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meaning // instanceof handles nulls
                && value.toLowerCase().equals(((Meaning) other).value.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
