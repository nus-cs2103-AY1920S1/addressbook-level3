package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Card's word.
 * Guarantees: immutable; is valid as declared in {@link #isValidWord(String)}
 */
public class Word {

    /** Maximum length of Word's string is set at 80 characters to ensure Hints showing properly. */
    private static final int MAX_LEN = 80;

    public static final String MESSAGE_CONSTRAINTS =
            "Words should be 1-" + MAX_LEN + " characters long, and not all are white spaces.";

    /*
     * The word should contain 1-80 characters, and not all are white spaces.
     */
    private static final String VALIDATION_REGEX = "^(?=.*\\S).{1," + MAX_LEN + "}$";

    private final String value;

    /**
     * Constructs a {@code Word}.
     *
     * @param word A valid word.
     */
    public Word(String word) {
        requireNonNull(word);
        checkArgument(isValidWord(word), MESSAGE_CONSTRAINTS);
        this.value = word;
    }

    /**
     * Returns true if a given string is a valid word.
     */
    public static boolean isValidWord(String test) {
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
                || (other instanceof Word // instanceof handles nulls
                && value.equals(((Word) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
