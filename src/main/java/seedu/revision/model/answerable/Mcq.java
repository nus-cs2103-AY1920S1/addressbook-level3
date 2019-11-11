package seedu.revision.model.answerable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Set;

import seedu.revision.model.category.Category;

/**
 * @author wilfredbtan
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Mcq extends Answerable {

    public static final String MESSAGE_CONSTRAINTS = "MCQs should only have 1 correct answer and 4 options in total"
            + " with no duplicate answers.";

    /**
     * Every field must be present and not null.
     */
    public Mcq(Question question, ArrayList<Answer> correctAnswerList, ArrayList<Answer> wrongAnswerList,
               Difficulty difficulty, Set<Category> categories) {
        super(question, correctAnswerList, wrongAnswerList, difficulty, categories);
    }


    /**
     * Checks whether the input Mcq is valid
     * @param mcq the mcq to validate.
     * @return boolean to indicate whether Mcq is valid or not.
     */
    public static boolean isValidMcq(Mcq mcq) {
        requireNonNull(mcq);
        if (mcq.getCorrectAnswerList().size() != 1
                || mcq.getWrongAnswerList().contains(mcq.getCorrectAnswerList().get(0))
                || mcq.getWrongAnswerList().size() != 3) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if both {@code Mcq}s with the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two {@code Mcq}s.
     */
    public boolean isSameAnswerable(Answerable otherAnswerable) {
        boolean generalAnswerableCheck = super.isSameAnswerable(otherAnswerable);
        return generalAnswerableCheck && otherAnswerable.getQuestion().equals(getQuestion())
                && otherAnswerable.getCorrectAnswerList().equals(getCorrectAnswerList());
    }

    /**
     * Returns an entire text string of the answerable (question with all possible answers,
     * difficulty level and categories)
     * @return answerable string
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: MCQ\n")
                .append("Question: ")
                .append(getQuestion() + "\n")
                .append("Correct Answer: " + getCorrectAnswerList() + "\n")
                .append("Wrong Answers: " + getWrongAnswerList() + "\n")
                .append("Difficulty: ")
                .append(getDifficulty() + "\n")
                .append("Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
