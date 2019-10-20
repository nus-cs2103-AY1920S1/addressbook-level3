package seedu.address.model.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a date of earnings.
 * Guarantees: immutable; is valid as declared in {@link #isValidAction(String)}
 */
public class CommandWord {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should only contain the date numbers that are valid (i.e. 03/05/2020), and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String word;

    /**
     * Constructs a {@code Date}.
     *
     * @param word A valid date number.
     */
    public CommandWord(String word) {
        requireNonNull(word);
        checkArgument(isValidWord(word), MESSAGE_CONSTRAINTS);
        this.word = word;
    }

    /**
     * Returns true if a given string is a valid week number.
     */
    public static boolean isValidWord(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandWord // instanceof handles nulls
                && word.equals(((CommandWord) other).word)); // state check
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

}
