package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a FlashCard's answer in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAnswer(String)}
 */
public class Answer {


    public static final String MESSAGE_CONSTRAINTS =
            "Answer can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String fullAnswer;

    /**
     * Constructs a {@code Answer}.
     *
     * @param answer A valid answer.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        fullAnswer = answer;
    }

    /**
     * Returns true if a given string is a valid answer.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullAnswer;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && fullAnswer.equals(((Answer) other).fullAnswer)); // state check
    }

    @Override
    public int hashCode() {
        return fullAnswer.hashCode();
    }

}
