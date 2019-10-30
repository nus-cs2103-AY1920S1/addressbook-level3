package seedu.revision.model.answerable;

import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Answerable {

    private static final Logger logger = Logger.getLogger(Answerable.class.getName());

    protected final Question question;
    protected final Difficulty difficulty;
    protected final ArrayList<Answer> correctAnswerList;
    protected final ArrayList<Answer> wrongAnswerList;
    protected final ArrayList<Answer> combinedAnswerList;
    protected final Set<Category> categories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Answerable(Question question, ArrayList<Answer> correctAnswerList,
                      ArrayList<Answer> wrongAnswerList, Difficulty difficulty, Set<Category> categories) {
        requireAllNonNull(question, difficulty, categories);
        this.question = question;
        this.correctAnswerList = correctAnswerList;
        this.wrongAnswerList = wrongAnswerList;
        ArrayList<Answer> shuffledList = new ArrayList<>();
        shuffledList.addAll(correctAnswerList);
        shuffledList.addAll(wrongAnswerList);
        Collections.shuffle(shuffledList);
        this.combinedAnswerList = shuffledList;
        this.difficulty = difficulty;
        this.categories.addAll(categories);
    }

    public Question getQuestion() {
        return question;
    }

    public ArrayList<Answer> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public ArrayList<Answer> getWrongAnswerList() {
        return wrongAnswerList;
    }

    public ArrayList<Answer> getCombinedAnswerList() {
        return combinedAnswerList;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    /**
     * Returns true if question has been answered correctly and false if it has been answered wrongly.
     * @param selectedAnswer answer that user selected.
     * @return true if correct or false if wrong.
     */
    public boolean isCorrect(Answer selectedAnswer) {
        if (correctAnswerList.contains(selectedAnswer)) {
            logger.info("correct answer selected");
            return true;
        }
        logger.info("WRONG answer selected");
        return false;
    }

    /**
     * Returns true if both answerables with the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two answerables.
     */
    public boolean isSameAnswerable(Answerable otherAnswerable) {
        if (otherAnswerable == this) {
            return true;
        }
        if (!(otherAnswerable.getClass().equals(this.getClass()))) {
            return false;
        }

        boolean isSameMCq = true;
        if (this instanceof Mcq) {
            isSameMCq = otherAnswerable.getWrongAnswerList().equals(getWrongAnswerList());
        }

        return otherAnswerable != null
            && otherAnswerable.getQuestion().equals(getQuestion())
            && otherAnswerable.getCorrectAnswerList().equals(getCorrectAnswerList())
            && otherAnswerable.getDifficulty().equals(getDifficulty())
            && isSameMCq;
    }

    /**
     * Returns true if both Answerables have the same identity and data fields.
     * This defines a stronger notion of equality between two Answerables.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Answerable)) {
            return false;
        }

        Answerable otherAnswerable = (Answerable) other;
        return otherAnswerable.getQuestion().equals(getQuestion())
                && otherAnswerable.getCorrectAnswerList().equals(getCorrectAnswerList())
                && otherAnswerable.getWrongAnswerList().equals(getWrongAnswerList())
                && otherAnswerable.getDifficulty().equals(getDifficulty())
                && otherAnswerable.getCategories().equals(getCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, correctAnswerList, wrongAnswerList, difficulty, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Question: ")
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
