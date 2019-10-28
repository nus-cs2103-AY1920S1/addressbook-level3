package seedu.revision.model.answerable.answer;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents a Answer in the revision tool.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAnswer(String)}
 */
public class McqAnswer implements Answer {

    public static final String MESSAGE_CONSTRAINTS = "Answers should not be blank, " +
            "and have 140 characters or less. Answers should not be duplicated.";
    public static final String VALIDATION_REGEX = "(.*\\n*.*)*";

    public final String answer;
    /**
     * Constructs a {@code McqAnswer}.
     *
     * @param answer A valid mcq answer.
     */
    public McqAnswer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        this.answer = answer;
    }

    @Override
    public boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Empty Mcq Answer used for validation.
     * @return empty Mcq Answer.
     */
    public static McqAnswer emptyMcqAnswer() {
        return new McqAnswer("");
    }

    @Override
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
