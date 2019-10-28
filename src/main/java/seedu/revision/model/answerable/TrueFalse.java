package seedu.revision.model.answerable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.answerable.answer.TfAnswer;
import seedu.revision.model.category.Category;

public class TrueFalse extends Answerable {
    public static final String MESSAGE_CONSTRAINTS = " Correct answer" +
            " should only be True/False (case insensitive)" +
            " Wrong answer should not be provided";

    private final static Logger logger = Logger.getLogger(TrueFalse.class.getName());
    /**
     * Every field must be present and not null.
     */
    public TrueFalse(Question question, ArrayList<Answer> correctAnswerList,
                     Difficulty difficulty, Set<Category> categories) {
        super(question, correctAnswerList, new ArrayList<>(), difficulty, categories);
    }

    @Override
    public boolean isCorrect(Answer selectedAnswer) {
        Answer caseInsensitiveAnswer = new TfAnswer(selectedAnswer.toString().toLowerCase());
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
                .append(" Wrong Answer: " + wrongAnswerList)
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }
}
