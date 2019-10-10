package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the type of Question
 */
public class QuestionType {

    public static final String MESSAGE_CONSTRAINTS =
            "Question type should only be mcq or saq";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String type;

    /**
     * Constructs a {@code QuestionType}.
     *
     * @param type A valid question type.
     */
    public QuestionType(String type) {
        requireNonNull(type);
//        checkArgument(isValidQuestionType(type), MESSAGE_CONSTRAINTS);
        this.type = type.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid question.
     */
    public boolean isValidQuestionType(String test) {
        switch(type) {
        case "mcq":
        //fallthrough
        case "saq":
            return test.matches(VALIDATION_REGEX);
        default:
            return false;
        }
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
