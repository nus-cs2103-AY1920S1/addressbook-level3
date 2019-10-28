package seedu.revision.model.answerable.answer;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

public class Answer {

    public static String MESSAGE_CONSTRAINTS = "Answers should not be repeated";
//    String TYPE_NOT_FOUND = "Question types can only be tf, mcq, or saq";
    public static final String VALIDATION_REGEX = "(.*\\n*.*)*";

    public final String answer;
    /**
     * Constructs a {@code Ansewr}.
     *
     * @param answer A valid answer.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        this.answer = answer;
    }

    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Empty Answer used for validation.
     * @return empty Answer.
     */
    public static Answer emptyAnswer() {
        return new Answer("");
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && answer.equals(((Answer) other).answer)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return answer;
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }

}
