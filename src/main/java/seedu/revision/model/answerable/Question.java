package seedu.revision.model.answerable;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents a Answerable's name in the revision tool.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuestion(String)}
 */
public class Question {

    public static final String MESSAGE_CONSTRAINTS = "Questions must be less that 300 characters and "
            + "should not be blank";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?=.{0,300}$)(?=\\s*\\S).*$";

    public final String question;

    /**
     * Constructs a {@code Question}.
     *
     * @param question A valid question.
     */
    public Question(String question) {
        requireNonNull(question);
        checkArgument(isValidQuestion(question), MESSAGE_CONSTRAINTS);
        this.question = question;
    }

    /**
     * Returns true if a given string is a valid question.
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
        return other == this // short circuit if same object
                || (other instanceof Question // instanceof handles nulls
                && question.equals(((Question) other).question)); // state check
    }

    @Override
    public int hashCode() {
        return question.hashCode();
    }

}
