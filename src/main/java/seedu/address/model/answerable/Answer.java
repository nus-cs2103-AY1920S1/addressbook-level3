package seedu.address.model.answerable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Answer in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAnswer(String)}
 */
public class Answer {

    public static final String MESSAGE_CONSTRAINTS = "Answers should be alphanumeric??";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String answer;

    /**
     * Constructs a {@code Answer}.
     *
     * @param answer A valid tag name.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        this.answer = answer;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && answer.equals(((Answer) other).answer)); // state check
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + answer + ']';
    }

}
