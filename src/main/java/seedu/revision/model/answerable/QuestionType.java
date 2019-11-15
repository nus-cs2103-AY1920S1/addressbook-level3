package seedu.revision.model.answerable;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents the type of Question
 */
public class QuestionType {

    public static final String MESSAGE_CONSTRAINTS =
            "Question type should only be mcq, tf, or saq";
    /*
     * The first character of the question type must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "(?i)mcq|saq|tf";

    private final String type;

    /**
     * Constructs a {@code QuestionType}.
     *
     * @param type A valid question type.
     */
    public QuestionType(String type) {
        requireNonNull(type);
        checkArgument(isValidQuestionType(type), MESSAGE_CONSTRAINTS);
        this.type = type.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid question.
     */
    public static boolean isValidQuestionType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionType // instanceof handles nulls
                && type.equals(((QuestionType) other).type)); // state check
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

}
