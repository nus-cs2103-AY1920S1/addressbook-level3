package seedu.address.model.answerable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AnswerSet {

    //TODO: Might change to multiple response
    public static final String MESSAGE_CONSTRAINTS = "MCQ answers should only be a, b, c or d";

    public Set<Answer> correctAnswerSet;
    public Set<Answer> wrongAnswerSet;

    /**
     * Default Constructor for Answer.
     */
    public AnswerSet() {
        this.correctAnswerSet = new HashSet<>();
        this.wrongAnswerSet = new HashSet<>();
    }

    public AnswerSet(Set<Answer> correctAnswerSet, Set<Answer> wrongAnswerSet) {
        this.correctAnswerSet = correctAnswerSet;
        this.wrongAnswerSet = wrongAnswerSet;
    }

    public boolean isCorrect(String answer) {
        //TODO: Implement isCorrect method
        return true;
    }

    public Set<Answer> getCorrectAnswerSet() {
        return correctAnswerSet;
    }

    public Set<Answer> getWrongAnswerSet() {
        return wrongAnswerSet;
    }

    public void setCorrectAnswerSet(Set<Answer> correctAnswerSet) {
        this.correctAnswerSet = correctAnswerSet;
    }

    public void setWrongAnswerSet(Set<Answer> wrongAnswerSet) {
        this.wrongAnswerSet = wrongAnswerSet;
    }

    @Override
    public String toString() {
        return "Correct Answers: " + Arrays.toString(correctAnswerSet.toArray())
                + "Wrong Answers: " + Arrays.toString(wrongAnswerSet.toArray());
    }

    /**
     * Returns true if both AnswerSets have the same correct and wrong answers
     * This defines a stronger notion of equality between two AnswerSets.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AnswerSet)) {
            return false;
        }

        AnswerSet otherAnswerSet = (AnswerSet) other;
        return otherAnswerSet.getCorrectAnswerSet().equals(getCorrectAnswerSet())
                && otherAnswerSet.getWrongAnswerSet().equals(getWrongAnswerSet());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(correctAnswerSet, wrongAnswerSet);
    }
}
