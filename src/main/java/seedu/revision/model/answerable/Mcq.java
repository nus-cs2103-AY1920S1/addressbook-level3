package seedu.revision.model.answerable;

import seedu.revision.model.category.Category;

import java.util.Arrays;
import java.util.Set;

import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Mcq extends Answerable {

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
        String correctAnswers;
        String wrongAnswers;
        if (wrongAnswerSet.isEmpty()) {
            correctAnswers = "Correct Answers: " + Arrays.toString(correctAnswerSet.toArray());
            wrongAnswers = "";
        } else {
            correctAnswers = "Correct Answers: " + Arrays.toString(correctAnswerSet.toArray());
            wrongAnswers = "Wrong Answers: " + Arrays.toString(wrongAnswerSet.toArray());
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("Question: ")
                .append(getQuestion())
                .append(" Answers: ")
                .append(correctAnswers)
                .append(wrongAnswers)
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
