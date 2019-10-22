package seedu.revision.model.answerable;

import seedu.revision.logic.commands.AddCommand;
import seedu.revision.logic.parser.QuestionType;
import seedu.revision.model.category.Category;

import java.util.Arrays;
import java.util.Set;

import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Mcq extends Answerable {

    public static final String MESSAGE_CONSTRAINTS = "MCQs should only have 1 correct answer and 4 options in total";

    /**
     * Every field must be present and not null.
     */
    public Mcq(Question question, Set<Answer> correctAnswerSet, Set<Answer> wrongAnswerSet,
               Difficulty difficulty, Set<Category> categories) {
        super(question, correctAnswerSet, wrongAnswerSet, difficulty, categories);
    }


    //TODO: Update functionality
    public boolean isCorrect() {
        return true;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: MCQ ")
                .append("Question: ")
                .append(getQuestion())
                .append(" Answers:")
                .append(" Correct Answers: " + getCorrectAnswerSet())
                .append(" Wrong Answers: " + getWrongAnswerSet())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
