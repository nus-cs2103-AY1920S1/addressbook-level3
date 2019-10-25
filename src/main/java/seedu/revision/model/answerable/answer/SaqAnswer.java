package seedu.revision.model.answerable.answer;


import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents a Answer in the revision tool.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAnswer(String)}
 */
public class SaqAnswer implements Answer {

    public static final String MESSAGE_CONSTRAINTS = "Answers should not be blank and cannot be duplicates";
    public static final String VALIDATION_REGEX = ".*";

    public final String answer;
    /**
     * Constructs a {@code McqAnswer}.
     *
     * @param answer A valid mcq answer.
     */
    public SaqAnswer(String answer) {
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
                || (other instanceof SaqAnswer // instanceof handles nulls
                && answer.equals(((SaqAnswer) other).answer)); // state check
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
