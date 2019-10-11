package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Flashcard's answer in the StudyBuddy app.
 * Guarantees: immutable; is valid as declared in {@link #isValidAnswer(String)}
 */
public class Answer {

    public static final String MESSAGE_CONSTRAINTS =
            "Answers should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the input must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullAnswer;

    /**
     * Constructs a {@code Name}.
     *
     * @param answer A valid question.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        fullAnswer = answer;
    }

    /**
     * Returns true if a given string is a valid name.
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
                || (other instanceof Question // instanceof handles nulls
                && fullAnswer.equals(((Question) other).fullQuestion)); // state check
    }

    @Override
    public int hashCode() {
        return fullAnswer.hashCode();
    }
}
