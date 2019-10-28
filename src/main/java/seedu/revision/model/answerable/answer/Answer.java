package seedu.revision.model.answerable.answer;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

public class Answer {

    String MESSAGE_CONSTRAINTS = "Answers should not be repeated";
    String TYPE_NOT_FOUND = "Question types can only be tf, mcq, or saq";
    public static final String VALIDATION_REGEX = "(.*\\n*.*)*";

    static boolean isValidAnswer(String test, String validationRegex) {
        return test.matches(validationRegex);
    }

    public final String answer;
    /**
     * Constructs a {@code McqAnswer}.
     *
     * @param answer A valid mcq answer.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        this.answer = answer;
    }

    public boolean isValidAnswer(String test) {
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
                || (other instanceof McqAnswer // instanceof handles nulls
                && answer.equals(((McqAnswer) other).answer)); // state check
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
