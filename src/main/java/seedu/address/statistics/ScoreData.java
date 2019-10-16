package seedu.address.statistics;

/**
 * Represents the score data for a game.
 */
public class ScoreData {

    public static final int MAX_SCORE = 100;
    public static final int MIN_SCORE = 0;

    private int score;

    public ScoreData(int score) {
        if (score > MAX_SCORE) {
            score = MAX_SCORE;
        } else if (score < MIN_SCORE) {
            score = MIN_SCORE;
        }
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return Integer.toString(score);
    }

    public static ScoreData max(ScoreData data1, ScoreData data2) {
        return data1.score > data2.score
                ? data1
                : data2;
    }
}
