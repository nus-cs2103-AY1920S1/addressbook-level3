package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Score attribute of {@code Team} and {@code Leaderboard}.
 *  * Guarantees: details are present and not null,
 *  * field values is  validated as declared in {@link #isValidScore(int)}, immutable.
 */
public class Score {

    public static final String MESSAGE_CONSTRAINTS = "Score should contain only positive integers from 0 to 100";
    public static final int MAX_SCORE = 100;
    public static final int MIN_SCORE = 0;
    public static final String MAX_SCORE_MESSAGE = "The given team's score is already " + MAX_SCORE
            + " which is the maximum allowed.";
    public static final String MIN_SCORE_MESSAGE = "The given team's score is already " + MIN_SCORE
            + " which is the minimum allowed.";

    private int score;


    /**
     * Constructs a {@code Score}
     *
     * @param score A valid score of a team.
     */
    public Score(int score) {
        requireNonNull(score);
        checkArgument(isValidScore(score), MESSAGE_CONSTRAINTS);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    /**
     * Returns if a given string is a valid score.
     *
     * @param score Score.
     * @return boolean whether score is in valid score format.
     */
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }


    /**
     * Returns string representation of object.
     *
     * @return Score in string format.
     */
    public String toString() {
        return Integer.toString(this.score);
    }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Score in string format.
     */
    public int toStorageValue() {
        return this.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.score);
    }

    @Override
    public boolean equals(Object other) {
        Score otherScore = ((Score) other);
        return otherScore == this | otherScore.getScore() == this.getScore();
    }

    /**
     * Returns a deep copy of the Score object
     * @return a deep copy of the Score object
     */
    public Score copy() {
        return new Score(this.score);
    }
}
