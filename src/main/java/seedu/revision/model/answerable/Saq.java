package seedu.revision.model.answerable;

import seedu.revision.model.category.Category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Saq extends Answerable {

    /**
     * Every field must be present and not null.
     */
    public Saq(Question question, ArrayList<Answer> correctAnswerList, Difficulty difficulty,
               Set<Category> categories) {
        //TODO: Find a better way to initialise this
        super(question, correctAnswerList, new ArrayList<>(), difficulty, categories);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: SAQ ")
                .append("Question: ")
                .append(" Correct Answers: ")
                .append(getCorrectAnswerList())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
