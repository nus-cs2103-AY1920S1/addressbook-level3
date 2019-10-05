package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Score attribute of {@code Team} and {@code Leaderboard}.
 *  * Guarantees: details are present and not null,
 *  * field values is  validated as declared in {@link #isValidScore(int)}, immutable.
 */
public class Score {

    public static final String MESSAGE_CONSTRAINTS = "Score should contain only positive integers from 0 to 100";
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
   public String toString(){
    return Integer.toString(this.score);
   }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Score in string format.
     */
   public int toStorageValue(){
    return this.score;
   }
}
