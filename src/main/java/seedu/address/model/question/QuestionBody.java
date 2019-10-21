package seedu.address.model.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a question's content body. Bodies are guaranteed to be unique among questions.
 * They are also immutable and guaranteed to be valid.
 */
public class QuestionBody {
    public static final String MESSAGE_CONSTRAINTS = "The question bodies can take any non-blank value.";

    public final String body;

    /**
     * Constructs a {@code Content} object.
     *
     * @param body Valid body.
     */
    public QuestionBody(String body) {
        requireNonNull(body);
        checkArgument(isValidQuestionBody(body), MESSAGE_CONSTRAINTS);
        this.body = body;
    }

    /**
     * Returns true if a given string represents valid body.
     */
    public static boolean isValidQuestionBody(String test) {
        return test.trim().length() > 0;
    }

    @Override
    public String toString() {
        return body;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionBody // instanceof handles nulls
                && body.equals(((QuestionBody) other).body)); // state check
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}
