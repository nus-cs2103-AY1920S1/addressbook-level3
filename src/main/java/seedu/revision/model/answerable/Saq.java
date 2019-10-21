package seedu.revision.model.answerable;

import seedu.revision.logic.parser.QuestionType;
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
    public Saq(Question question, Set<Answer> correctAnswerSet, Difficulty difficulty,
               Set<Category> categories) {
        //TODO: Find a better way to initialise this
        super(question, correctAnswerSet, null, difficulty, categories);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: SAQ ")
                .append("Question: ")
                .append(" Correct Asnwers: ")
                .append(getCorrectAnswerSet())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
