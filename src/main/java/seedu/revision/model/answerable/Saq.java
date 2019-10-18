package seedu.revision.model.answerable;

import seedu.revision.model.category.Category;

import java.util.Arrays;
import java.util.Set;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Saq extends Answerable {

    /**
     * Every field must be present and not null.
     */
    public Saq(Question question, Set<Answer> correctAnswerSet, Set<Answer> wrongAnswerSet, Difficulty difficulty,
               Set<Category> categories) {
        super(question, correctAnswerSet, wrongAnswerSet, difficulty, categories);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Question: ")
                .append(getQuestion())
                .append(" Answers: ")
                .append("Correct Answers: " + Arrays.toString(correctAnswerSet.toArray()))
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
