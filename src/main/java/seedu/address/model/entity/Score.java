package seedu.address.model.entity;

public class Score {
    private int score;

    /**
     * Constructor.
     *
     * @param score
     */
    public Score(int score) {
        this.score = score;
    }

    /**
     * Checks if the score is valid.
     *
     * @param score
     * @return boolean
     */
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }
}
