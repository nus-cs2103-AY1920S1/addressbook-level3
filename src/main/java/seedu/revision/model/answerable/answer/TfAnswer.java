package seedu.revision.model.answerable.answer;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents a Answer in the revision tool.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAnswer(String)}
 */
public class TfAnswer implements Answer {

    public static final String MESSAGE_CONSTRAINTS = "Answer can only be True or False";
    public static final String VALIDATION_REGEX = "(?i)(true|false)";

    public final String answer;
    /**
     * Constructs a {@code McqAnswer}.
     *
     * @param answer A valid mcq answer.
     */
    public TfAnswer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        this.answer = answer;
    }


    @Override
    public boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TfAnswer // instanceof handles nulls
                && answer.equals(((TfAnswer) other).answer)); // state check
    }

    @Override
    public String toString() {
        return answer;
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }


}
