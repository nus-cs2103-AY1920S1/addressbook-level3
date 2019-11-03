package seedu.revision.model.answerable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import seedu.revision.model.category.Category;

/** TrueFalse class used to create True and False answerables. **/
public class TrueFalse extends Answerable {
    /** Message to be shown if user-added True and False {@code Answerable}is not is in the wrong format**/
    public static final String MESSAGE_CONSTRAINTS = " T/F answer"
            + " should only be True/False (case insensitive)";
    /** Validation Regex for the TrueFalse class used to validate user-added True and False {@code Answerable}. **/
    public static final String VALIDATION_REGEX = "(?i)(true|false)";

    private static final Logger logger = Logger.getLogger(TrueFalse.class.getName());

    /**
     * Every field must be present and not null.
     */
    public TrueFalse(Question question, ArrayList<Answer> correctAnswerList,
                     Difficulty difficulty, Set<Category> categories) {
        super(question, correctAnswerList, new ArrayList<>(), difficulty, categories);
    }

    /**
     * Empty TrueFalse Answer used for validation.
     * @return empty TrueFalse Answer.
     */
    public static boolean isValidTrueFalse(TrueFalse trueFalse) {
        requireNonNull(trueFalse);
        if (trueFalse.getCorrectAnswerList().stream()
                .anyMatch(a -> a.getAnswer().matches(VALIDATION_REGEX))
                || trueFalse.getWrongAnswerList().stream()
                .anyMatch(a -> a.getAnswer().matches(VALIDATION_REGEX))) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if both TrueFalse questions have the same question only.
     * This defines a weaker notion of equality between two TrueFalse questions.
     */
    @Override
    public boolean isSameAnswerable(Answerable otherAnswerable) {
        boolean generalAnswerableCheck = super.isSameAnswerable(otherAnswerable);
        return generalAnswerableCheck && otherAnswerable.getQuestion().equals(getQuestion());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: T/F\n")
                .append("Question: ")
                .append(getQuestion() + "\n")
                .append("Correct Answer: " + getCorrectAnswerList() + "\n")
                .append("Difficulty: ")
                .append(getDifficulty() + "\n")
                .append("Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
