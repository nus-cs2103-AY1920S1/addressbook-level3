package seedu.address.storage.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.statistics.CardStatistics;
import seedu.address.statistics.ScoreData;

public class JsonAdaptedScoreData {
    private final int score;

    public JsonAdaptedScoreData(@JsonProperty("score") int score) {
        this.score = score;
    }

    /**
     * Converts a given {@code ScoreData} into this class for Jackson use.
     */
    public JsonAdaptedScoreData(ScoreData source) {
        this.score = source.getScore();
    }

    public ScoreData toModelType() {
        return new ScoreData(score);
    }

}
