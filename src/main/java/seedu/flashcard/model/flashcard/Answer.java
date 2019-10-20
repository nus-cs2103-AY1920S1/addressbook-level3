package seedu.flashcard.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.AppUtil.checkArgument;

/**
 * The answer is just one of the choices in the flashcard.
 * Its message must exactly fit the correct choice.
 */
public class Answer {

    public static final String MESSAGE_CONSTRAINTS = "Answer can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String answer;

    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer));
        this.answer = answer;
    }

    /**
     * Returns true if a given string is a valid choice.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Answer
            && answer.equals(((Answer) other).answer));
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }

    @Override
    public String toString() {
        return answer + '\n';
    }

}
