package seedu.revision.model.answerable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Set;

import seedu.revision.model.category.Category;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Saq extends Answerable {

    public static final String MESSAGE_CONSTRAINTS = "SAQs should have at least one correct answer"
            + " and no wrong answers.";
    public static final String MESSAGE_INVALID_ANSWER_EXIT = "The answer cannot be 'exit'. "
            + "'Exit' is a special command";
    public static final String MESSAGE_INVALID_ANSWER = "Answers should not appear in the question.";

    /**
     * Every field must be present and not null.
     */
    public Saq(Question question, ArrayList<Answer> correctAnswerList, Difficulty difficulty,
               Set<Category> categories) {
        super(question, correctAnswerList, new ArrayList<>(), difficulty, categories);
    }

    /**
     * Checks whether the input Mcq is valid
     * @param saq the saq to validate.
     * @return boolean to indicate whether Mcq is valid or not.
     */
    public static boolean isValidSaq(Saq saq) {
        requireNonNull(saq);
        if (saq.getCorrectAnswerList().size() == 0
                || saq.getWrongAnswerList().size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCorrect(Answer selectedAnswer) {
        boolean answerIsCorrect = AnswerChecker.check(selectedAnswer.toString(), this);
        return answerIsCorrect;
    }

    /**
     * Returns true if both {@code Saq}s with the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two {@code Saq}s.
     */
    public boolean isSameAnswerable(Answerable otherAnswerable) {
        boolean generalAnswerableCheck = super.isSameAnswerable(otherAnswerable);
        return generalAnswerableCheck && otherAnswerable.getQuestion().equals(getQuestion());
    }

    /**
     * Returns an entire text string of the answerable (question with all possible answers,
     * difficulty level and categories)
     * @return answerable string
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: SAQ\n")
                .append("Question: ")
                .append(getQuestion() + "\n")
                .append("Correct Answers: ")
                .append(getCorrectAnswerList() + "\n")
                .append("Difficulty: ")
                .append(getDifficulty() + "\n")
                .append("Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
