package seedu.address.statistics;

public class ScoreData {
    private int score;

    public ScoreData(int score) {
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
