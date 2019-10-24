package seedu.revision.model.answerable;

import seedu.revision.model.category.Category;

import java.util.ArrayList;
import java.util.Set;

import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Mcq extends Answerable {

    public static final String MESSAGE_CONSTRAINTS = "MCQs should only have 1 correct answer and 4 options in total";

    /**
     * Every field must be present and not null.
     */
    public Mcq(Question question, ArrayList<Answer> correctAnswerList, ArrayList<Answer> wrongAnswerList,
               Difficulty difficulty, Set<Category> categories) {
        super(question, correctAnswerList, wrongAnswerList, difficulty, categories);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: MCQ ")
                .append("Question: ")
                .append(getQuestion())
                .append(" Answers:")
                .append(" Correct Answers: " + getCorrectAnswerList())
                .append(" Wrong Answers: " + getWrongAnswerList())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
