package seedu.address.model.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a question's answer. Answers are guaranteed to be unique among questions.
 * They are also immutable and guaranteed to be valid.
 */
public class Answer {
    public static final String MESSAGE_CONSTRAINTS = "The answers can take any non-blank value.";

    public final String answer;

    /**
     * Constructs a {@code Answer} object.
     *
     * @param answer Valid answer.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidArgument(answer), MESSAGE_CONSTRAINTS);
        this.answer = answer;
    }

    /**
     * Returns true if a given string represents valid answer.
     */
    public static boolean isValidArgument(String test) {
        return test.trim().length() > 0;
    }

    @Override
    public String toString() {
        return answer;
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
}
