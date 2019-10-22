package seedu.flashcard.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.AppUtil.checkArgument;

/**
 * The word itself of the flashcard.
 * TODO: Can be replaced by the flashcard question.
 */
public class Question {

    public static final String MESSAGE_CONSTRAINTS =
            "Questions should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String question;

    public Question(String question) {
        requireNonNull(question);
        checkArgument(isValidQuestion(question), MESSAGE_CONSTRAINTS);
        this.question = question;
    }

    /**
     * Returns true if a giving string is a valid word.
     */
    public static boolean isValidQuestion(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return question;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Question)
                && question.equals(((Question) other).question);
    }

    @Override
    public int hashCode() {
        return question.hashCode();
    }
}
