package seedu.revision.model.answerable.answer;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/** Answer class used to create answers for {@code Answerable}s. **/
public class Answer {

    /** Message to be shown if user-added answer is not is in the wrong format**/
    public static final String MESSAGE_CONSTRAINTS = "Answers must be less than 100 characters "
            + "and should be not be blank.";

    /** Validation Regex for the Answer class used to validate user-added answers. **/
    public static final String VALIDATION_REGEX = "((?=.{0,100}$).*\\n*.*)*";

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
