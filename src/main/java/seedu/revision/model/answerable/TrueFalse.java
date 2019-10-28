package seedu.revision.model.answerable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;

import static java.util.Objects.requireNonNull;

public class TrueFalse extends Answerable {
    public static final String MESSAGE_CONSTRAINTS = " T/F answer"
            + " should only be True/False (case insensitive)";
    public static final String VALIDATION_REGEX = "(?i)(true|false)";

    private final static Logger logger = Logger.getLogger(TrueFalse.class.getName());

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
//        if (trueFalse.getCorrectAnswerList().size() != 1 || trueFalse.getWrongAnswerList().size() != 1) {
//            return false;
//        }
        if (trueFalse.getCorrectAnswerList().stream()
                .anyMatch(a -> a.getAnswer().matches(VALIDATION_REGEX)) ||
                trueFalse.getWrongAnswerList().stream()
                .anyMatch(a -> a.getAnswer().matches(VALIDATION_REGEX))) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isCorrect(Answer selectedAnswer) {
        Answer caseInsensitiveAnswer = new Answer(selectedAnswer.toString().toLowerCase());
        if (correctAnswerList.contains(caseInsensitiveAnswer)) {
            logger.info("correct answer selected");
            return true;
        }
        logger.info("WRONG answer selected");
        return false;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        List<Answer> wrongAnswerList =
                this.combinedAnswerList
                    .stream()
                    .filter(ans -> !this.getCorrectAnswerList().contains(ans))
                    .collect(Collectors.toList());

        builder.append("Type: T/F ")
                .append("Question: ")
                .append(getQuestion())
                .append(" Answers:")
                .append(" Correct Answer: " + getCorrectAnswerList())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
