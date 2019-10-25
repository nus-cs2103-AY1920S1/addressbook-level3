package seedu.revision.model.answerable;

import java.util.ArrayList;
import java.util.Set;


import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;

public class TrueFalse extends Answerable {
    public static final String MESSAGE_CONSTRAINTS = " Correct/Wrong answers should only be True/False" +
            " and be mutually exclusive";

    /**
     * Every field must be present and not null.
     */
    public TrueFalse(Question question, ArrayList<Answer> correctAnswerList, ArrayList<Answer> wrongAnswerList,
                     Difficulty difficulty, Set<Category> categories) {
        super(question, correctAnswerList, wrongAnswerList, difficulty, categories);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append("Type: T/F ")
                .append("Question: ")
                .append(getQuestion())
                .append(" Answers:")
                .append(" Correct Answer: " + getCorrectAnswerList())
                .append(" Wrong Answer: " + getWrongAnswerList())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
