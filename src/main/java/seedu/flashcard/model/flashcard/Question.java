package seedu.flashcard.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.AppUtil.checkArgument;

/**
 * The question of the flashcard.
 */
public class Question {

    public static final String MESSAGE_CONSTRAINTS =
            "Questions should only contain alphanumeric characters, "
                + "normal symbols like question marks and commas and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "\\S.*";

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

    /**
     * Transform this question to become a label for the statistics bar graph.
     * Only takes the first five words of the question.
     */
    public String shortenForLabel() {
        String[] splitQuestion = question.split("\\s+");
        String label = "";
        int labelLength = 6;
        for (int i = 0; i < labelLength; i++) {
            if (i == splitQuestion.length) {
                break;
            }
            label = label + splitQuestion[i] + " ";
            if (i == labelLength - 1) {
                label = label + "...";
            }
        }
        if (splitQuestion.length > labelLength) {
            label = label + " " + splitQuestion[splitQuestion.length - 1];
        }
        return label;
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
